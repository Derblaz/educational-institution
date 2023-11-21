import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RefreshObserverService {
  private observer = new Subject<string>();

  observable() {
    return this.observer.asObservable();
  }

  emit(mensagem: string) {
    this.observer.next(mensagem);
  }
}
