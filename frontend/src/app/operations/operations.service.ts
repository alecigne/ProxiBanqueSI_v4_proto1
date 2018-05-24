import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class OperationsService {

  constructor(private http: HttpClient,
  @Inject('BACKEND_URL') private baseURL: string
) { }

  virement(numCompteDepart, numCompteArrivee, montant):Promise<any>{
    console.log({ numCompteDepart:+numCompteDepart,
      numCompteArrivee:+numCompteArrivee,
      montantTransfere:montant})
    return this.http.put(this.baseURL + 'virement',
    { numCompteDepart:+numCompteDepart,
       numCompteArrivee:+numCompteArrivee,
       montantTransfere:montant} ).toPromise();
  }

}
