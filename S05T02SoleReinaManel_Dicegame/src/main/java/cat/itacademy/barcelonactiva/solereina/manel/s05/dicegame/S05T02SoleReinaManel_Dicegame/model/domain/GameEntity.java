package cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.domain;

import cat.itacademy.barcelonactiva.solereina.manel.s05.dicegame.S05T02SoleReinaManel_Dicegame.model.utils.GameUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pk_GameId;
    //@Column(name = "player_id")
    //private int playerId; //TODO delete
    @Column(name = "die_one")
    private int die1;
    @Column(name = "die_two")
    private int die2;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PlayerEntity player;

    public GameEntity() {
    }
/*
    public GameEntity(int playerId) {
        this.playerId = playerId;
    }
*/
    public GameEntity(PlayerEntity player) {
        this.player = player;
    }
    public boolean play() {
        die1 = (int) (Math.random()*5 + 1);
        die2 = (int) (Math.random()*5 + 1);

        return validateVictory(this);
    }

    public static boolean validateVictory(GameEntity game) {
        return game.getDie1() + game.getDie2() == 7;
    }
}
