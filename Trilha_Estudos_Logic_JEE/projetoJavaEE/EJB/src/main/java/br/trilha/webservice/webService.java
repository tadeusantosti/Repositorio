package br.trilha.webservice;

import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService
@Stateless
public class webService {
    public String getTeste(){
        return "Ola Mundo";
    }
}
