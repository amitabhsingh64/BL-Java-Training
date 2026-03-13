package org.amitabh.mycontactapp2.service;


import org.amitabh.mycontactapp2.model.Contact;
import org.amitabh.mycontactapp2.repository.ContactRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class contactService {
    private final ContactRepository contactRepository;
}
