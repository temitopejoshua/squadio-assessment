package com.squadio.services;

import com.squadio.requestDTO.BankStatementSearchRequest;
import com.squadio.responseDto.AccountResponseDTO;
import com.squadio.responseDto.AccountStatementResponseDTO;
import com.squadio.responseDto.UserResponseDTO;
import com.squadio.security.AuthenticatedUser;

import java.util.List;

public interface GetAccountDetailsService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserByUsername(AuthenticatedUser currentUser, String username);
    List<AccountResponseDTO> getAllAccountByUser(AuthenticatedUser currentUser,String userId);
    List<AccountStatementResponseDTO> getAccountStatement(AuthenticatedUser currentUser, BankStatementSearchRequest searchRequest);

}
