package cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.services;

import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.dto.GameDTO;

import java.util.List;

public interface GameService {
    GameDTO playGame(int playerID);
    List<GameDTO> getById(int id);
    GameDTO save(GameDTO game);
    void deleteByPlayer(int playerID);

    double getPlayerAverage(int playerID);
}
