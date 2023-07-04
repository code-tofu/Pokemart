package tfip.b3.mp.pokemart.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class ControllerUtil {

    public static ResponseEntity<String> exceptionHandler(Exception ex) {
        if (ex instanceof NumberFormatException) {
            System.err.println(">> [ERROR] " + ex);
            JsonObject resp = Json.createObjectBuilder()
                    .add("400 Bad Request Error", "Request Params needs to be a integer").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toString());
        }
        if (ex instanceof EmptyResultDataAccessException) {
            System.err.println(">> [ERROR] " + ex);
            JsonObject resp = Json.createObjectBuilder().add("404 Not Found Error", "Requested Resource Does Not Exist In Database")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp.toString());
        }
        if (ex instanceof DataAccessException) {
            System.err.println(">> [ERROR] " + ex);
            JsonObject resp = Json.createObjectBuilder().add("500 Internal Server Error", "Request Failed: Database Error")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp.toString());
        }
        if (ex instanceof JsonProcessingException) {
            System.err.println(">> [ERROR] " + ex);
            JsonObject resp = Json.createObjectBuilder().add("500 Internal Server Error", "Request Failed: JSON Format Error")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp.toString());
        }
        JsonObjectBuilder jsonOB = Json.createObjectBuilder().add("Error", ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonOB.build().toString());
    }

}
