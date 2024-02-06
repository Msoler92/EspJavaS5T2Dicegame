package cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.services;

import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.repositories.GameRepository;
import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.repositories.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PlayerServiceImpl implements PlayerService{
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GameRepository gameRepository;

    @Override
    public PlayerDTO getById(int id) {
        Optional<PlayerEntity> playerData = playerRepository.findById(id);
        if (playerData.isEmpty()) {
            throw new PlayerNotFoundException("Id not found.");
        }
        return entityToDTO(playerData.get());
    }
    //TODO verify player exists or not
    @Override
    public PlayerDTO save(PlayerDTO player) {
        return entityToDTO(
                playerRepository.save(
                        dtoToEntity(player)));
    }

    @Override
    public PlayerDTO update(PlayerDTO player) {
        getById(player.getId());
        return entityToDTO(
                playerRepository.save(
                        dtoToEntity(player)));
    }
    //TODO include average scores (through DTO? -> CUSTOM QUERY ON PLAYER REPO -> Modify PlayerEntity and GameEntity with one-to-many relation (eugh))
    @Override
    public List<PlayerDTO> getAll() {
        return playerRepository.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public List<PlayerDTO> getAllWithAverages() {
        List<GameEntity> games = gameRepository.findAll();
        return playerRepository.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public double getAverageRanking() {
        double average;
        List<PlayerDTO> playerDTOList = getAll();
        if (playerDTOList.isEmpty()) {
            average = 0;
        } else {
            average = playerDTOList.stream().mapToDouble(PlayerDTO::getVictoryRate).sum()/playerDTOList.size();
        }
        return average;
    }

    @Override
    public PlayerDTO getLoser() {
        return null;
    }

    @Override
    public PlayerDTO getWinner() {
        return null;
    }

    private double getPlayerAverage(int playerId) { //TODO Dirty. Repeated code from GameService. Multiple calls. Fix with two-way OneToMany-ManyToOne?
        double average;
        List<GameEntity> games = gameRepository.findByPlayerId(playerId);

        //TODO Handle empty game list differently?
        if (games.isEmpty()) {
            average = 0;
        } else {
            average = games.stream().filter(GameEntity::validateVictory).count();
            average /= games.size();
        }
        return average;
    }
    private PlayerDTO entityToDTO(PlayerEntity entity) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<PlayerEntity, PlayerDTO> propertyMapper = mapper.createTypeMap(PlayerEntity.class, PlayerDTO.class);
        propertyMapper.addMapping(player -> getPlayerAverage(player.getId()), PlayerDTO::setVictoryRate);
        PlayerDTO dto = new PlayerDTO();

        mapper.map(entity, dto);
        return dto;
    }

    private PlayerEntity dtoToEntity(PlayerDTO dto) {
        ModelMapper mapper = new ModelMapper();
        PlayerEntity entity = new PlayerEntity();

        mapper.map(dto, entity);
        return entity;
    }
}
