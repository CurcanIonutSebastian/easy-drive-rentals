package com.ionut.easydriverentals.services;

import com.ionut.easydriverentals.models.dtos.ClientDetailsDTO;

import java.util.zip.DataFormatException;

public interface ClientDetailsService {

    ClientDetailsDTO createClientDetails(Long clientId, ClientDetailsDTO clientDetailsDTO) throws DataFormatException;
}