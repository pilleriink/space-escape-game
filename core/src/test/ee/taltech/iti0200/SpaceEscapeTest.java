package ee.taltech.iti0200;

import ee.taltech.iti0200.entities.Enemy0;
import ee.taltech.iti0200.entities.EntityType;
import ee.taltech.iti0200.entities.OtherPlayer;
import ee.taltech.iti0200.entities.PlayerType;
import ee.taltech.iti0200.server.packets.Ability;
import ee.taltech.iti0200.server.packets.Death;
import ee.taltech.iti0200.world.GameMap;
import ee.taltech.iti0200.world.TiledGameMap;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.print.attribute.standard.MediaSize;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SpaceEscapeTest {

    @org.junit.Before
    public void BeforeEach() {
    }

    @org.junit.Test
    public void ability() {
        Ability ability = new Ability();
        ability.texture = "texture";
        ability.id = "id";
        ability.x = 0;
        ability.y = 0;
        assertSame("texture", ability.texture);
    }

    @org.junit.Test
    public void death() {
        Death death = new Death();
        death.id = "ii";
        assertSame("ii", death.id);
    }

    @org.junit.Test
    public void getIdEntityType() {
        EntityType entityType = EntityType.PLAYER;
        assertEquals("player", entityType.getId());
    }

    @org.junit.Test
    public void getWidthEntityType() {
        EntityType entityType = EntityType.ENEMY0;
        assertEquals(14, entityType.getWidth());
    }

    @org.junit.Test
    public void getHeightEntityType() {
        EntityType entityType = EntityType.ENEMY1;
        assertEquals(54, entityType.getHeight());
    }

}
