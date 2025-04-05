package sn.bfrost.myafiback.service;

import sn.bfrost.myafiback.service.dto.AuthenticationRequest;
import sn.bfrost.myafiback.service.dto.AuthenticationResponse;
import sn.bfrost.myafiback.service.dto.RegisterRequest;


public interface AuthenticationService {


     AuthenticationResponse authenticate(AuthenticationRequest request);

     AuthenticationResponse refreshToken(AuthenticationResponse refresh);

     AuthenticationResponse resetPassword(AuthenticationRequest request);


}
