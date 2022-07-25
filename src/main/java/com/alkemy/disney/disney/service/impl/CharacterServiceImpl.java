package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.dto.CharacterFiltersDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.enumuration.ErrorEnum;
import com.alkemy.disney.disney.exceptions.ParamNotFound;
import com.alkemy.disney.disney.mapper.CharacterMapper;
import com.alkemy.disney.disney.repository.CharacterRepository;
import com.alkemy.disney.disney.repository.specifications.CharacterSpecification;
import com.alkemy.disney.disney.service.CharacterService;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterMapper characterMapper;
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private MovieService movieService;

    private CharacterSpecification characterSpecification;


    public CharacterDTO save(CharacterDTO dto) {
        CharacterEntity entity = characterMapper.characterDTO2CharacterEntity(dto);
        CharacterEntity entitySaved = characterRepository.save(entity);
        return characterMapper.characterEntity2DTO(entitySaved, true);
    }

    @Override
    public CharacterDTO editCharacter(Long id, CharacterDTO dto) {
        Optional<CharacterEntity> character = characterRepository.findById(id);
        if(!character.isPresent()) {
            throw new EntityNotFoundException( ErrorEnum.ID_CHARACTER_NOT_FOUND.getMessage());
        }
        characterMapper.characterEntityRefreshValues(character.get(), dto);
        CharacterEntity characterEdited = characterRepository.save(character.get());
        return characterMapper.characterEntity2DTO(characterEdited, true);
    }

    @Override
    public void delete(Long id) {
        characterRepository.deleteById(id);
    }

    public CharacterEntity getEntityById(Long id) {
        return characterRepository.getById(id);
    }

    /*
    public void addMovie(Long id, Long idMovie) {
        CharacterEntity character = characterRepository.getById(id);
        MovieEntity movieEntity = movieService.getEntityById(idMovie);
        character.addMovie(movieEntity);
        characterRepository.save(character);
    }

    public void removeMovie(Long id, Long idMovie) {
        CharacterEntity character = characterRepository.getById(id);
        MovieEntity movieEntity = movieService.getEntityById(idMovie);
        character.removeMovie(movieEntity);
        characterRepository.save(character);
    }*/

    public CharacterDTO getDetailsById(Long id) {
        Optional<CharacterEntity> entity = characterRepository.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException( ErrorEnum.ID_CHARACTER_NOT_FOUND.getMessage());
        }
        return characterMapper.characterEntity2DTO(entity.get(), true);
    }

    public List<CharacterBasicDTO> getByFilters(String name, String age, List<Long> movies, String order) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, movies, order);
        List<CharacterEntity> entities = characterRepository.findAll(this.characterSpecification.getByFilters(filtersDTO));
        return this.characterMapper.characterEntitySet2DTOFiltersList(entities);
    }

}
