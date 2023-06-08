import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  searchQuery: FormControl = new FormControl<string>(
    '',
    Validators.minLength(3)
  );
  router = inject(Router);

  search() {
    if (this.searchQuery.invalid || this.searchQuery.value.trim() < 3) {
      alert('Please key in a longer query of more than 3 characters');
    } else {
      this.router.navigate(['/shop', 'search'], {
        queryParams: { query: this.searchQuery.value.trim() },
      });
    }
  }
}
