package com.portfolio.api.controller;

import com.portfolio.api.dto.Response;
import com.portfolio.api.dto.ContactDto;
import com.portfolio.api.entity.Contact;
import com.portfolio.api.service.ContactService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "https://fir-portfolio-220a9.web.app")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> list(){
        List<Contact> list = contactService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{id}")
public ResponseEntity<Response<Contact>> getById(@PathVariable("id") int id) {
    try {
        Contact contact = contactService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<Contact>(contact));
    } catch (NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("No existe"));
    }
    
}

    
    @PutMapping("/{id}")
    public ResponseEntity<Response<Contact>> update(@PathVariable("id")Integer id, @RequestBody ContactDto contactDto){

        try {
            Contact contact = contactService.update(id, contactDto);
            return ResponseEntity.status(HttpStatus.OK).body(new Response<Contact>("Contact actualizado", contact));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("No existe"));
        }

        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        try {
            Contact contact = contactService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Response<Contact>("Contact eliminado", contact));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("No existe"));
        }
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ContactDto contactDto){
        Contact contact = contactService.add(contactDto);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<Contact>("Contact creado", contact));
    }


}
