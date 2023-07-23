package tfip.b3.mp.pokemart.repository;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TTLRepository {

    @Autowired
    @Qualifier("TTL")
    RedisTemplate<String, String> redisTemplate;
    private static final int DEFAULT_TTL = 5;

    public void newTTL(String key, String value, int minutes) {
        this.redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(minutes));
	}

    public void newTTL(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(DEFAULT_TTL));
	}

	public boolean existsTTL(String key) {
        if(null == this.redisTemplate.opsForValue().get(key)) return false;
		return true;
	}

    public Optional<String> getValue(String key) {
        String value = this.redisTemplate.opsForValue().get(key);
        if(null == value) return Optional.empty();
        return Optional.of(value);
    }

    public boolean endTTL(String key){
        if(null == this.redisTemplate.opsForValue().getAndDelete(key)) return false;
		return true;
    }


    


}
