package com.squadio.controller;

import com.squadio.requestDTO.BankStatementSearchRequest;
import com.squadio.responseDto.AccountResponseDTO;
import com.squadio.responseDto.AccountStatementResponseDTO;
import com.squadio.responseDto.ApiResponse;
import com.squadio.responseDto.UserResponseDTO;
import com.squadio.security.AuthenticatedUser;
import com.squadio.services.GetAccountDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
@Slf4j
public class AccountController {
    private GetAccountDetailsService getAccountDetailsService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> findAllUsers(@AuthenticationPrincipal AuthenticatedUser authenticatedUser){
        List<UserResponseDTO> users = getAccountDetailsService.getAllUsers();
        ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>("Processed Successfully", users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> findUserByUsername(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable("username") String username){
        UserResponseDTO user = getAccountDetailsService.getUserByUsername(authenticatedUser,username);
        ApiResponse<UserResponseDTO> response = new ApiResponse<>("Processed Successfully", user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ApiResponse<List<AccountResponseDTO>>> getAccountByUserid(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable("userId") String userId){
        List<AccountResponseDTO> userAccount = getAccountDetailsService.getAllAccountByUser(authenticatedUser,userId);
        ApiResponse<List<AccountResponseDTO>> response = new ApiResponse<>("Processed Successfully", userAccount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/statement/{accountId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ApiResponse<List<AccountStatementResponseDTO>>> getAccountStatement(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                                                                              @PathVariable("accountId") String accountId,
                                                                                                 @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") String fromDate,
                                                                                               @RequestParam(value = "toDate", required = false)  @DateTimeFormat(pattern = "dd/MM/yyyy") String toDate,
                                                                                              @RequestParam(value = "fromAmount", required = false) BigDecimal fromAmount,
                                                                                              @RequestParam(value = "toAmount", required = false) BigDecimal toAmount

                                                                                              ){
        BankStatementSearchRequest searchRequest = BankStatementSearchRequest.builder().accountId(accountId)
                .build();

        if (!StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)) {
            log.info("start date - {}; end date - {}", fromDate, toDate);
            searchRequest.setFromDate(LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            searchRequest.setToDate(LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if(fromAmount !=null && toAmount !=null){
            searchRequest.setFromAmount(fromAmount);
            searchRequest.setToAmount(toAmount);
        }
        List<AccountStatementResponseDTO> accountStatement = getAccountDetailsService.getAccountStatement(authenticatedUser,searchRequest);
        ApiResponse<List<AccountStatementResponseDTO>> response = new ApiResponse<>("Processed Successfully", accountStatement);
        return ResponseEntity.ok(response);
    }

}
