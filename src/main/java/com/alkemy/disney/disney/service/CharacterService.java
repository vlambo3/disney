package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;;
import com.alkemy.disney.disney.entity.CharacterEntity;

import java.util.List;


public interface CharacterService {
    CharacterDTO save(CharacterDTO dto);
    CharacterDTO editCharacter(Long id, CharacterDTO dto);
    void delete(Long id);
    CharacterEntity getEntityById(Long id);
    /*
    void addMovie(Long id, Long idMovie);
    void removeMovie(Long id, Long idMovie);*/

    CharacterDTO getDetailsById(Long id);
    List<CharacterBasicDTO> getByFilters(String name, String age, Double weight, List<Long> movies, String order);
}
