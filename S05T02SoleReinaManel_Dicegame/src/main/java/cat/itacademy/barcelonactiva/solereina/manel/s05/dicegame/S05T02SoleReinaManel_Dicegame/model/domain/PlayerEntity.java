package cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.domain;

import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.utils.PlayerUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "players")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "player_name")
    private String playerName;
    @Column(name = "creation_date")
    private Date creationDate;

    public PlayerEntity() {
        this.playerName = PlayerUtils.DEFAULT_NAME;
    }
    public PlayerEntity(String playerName) {
        this.playerName = playerName;
    }

}
