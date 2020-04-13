package ee.taltech.iti0200.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer extends Game {
    Server server;
    Map<Connection, Player> players;
    List<Enemy> enemies;

    public GameServer() throws IOException {
        players = new HashMap<>();
        enemies = new ArrayList<>();

        Enemy enemy = new Enemy();
        enemy.enemyType = "enemy0";
        enemy.id = LocalDateTime.now().toString();
        enemy.lives = 50;
        enemy.x = 1000;
        enemy.y = 600;

        Enemy enemy1 = new Enemy();
        enemy1.enemyType = "enemy1";
        enemy1.id = LocalDateTime.now().toString();
        enemy1.lives = 50;
        enemy1.x = 1000;
        enemy1.y = 600;

        //enemies.add(enemy);
        //enemies.add(enemy1);

        Server server = new Server();
        server.start();
        server.bind(54556, 54778);
        Kryo kryoServer = server.getKryo();
        kryoServer.register(Register.class);
        kryoServer.register(Move.class);
        kryoServer.register(LivesLost.class);
        kryoServer.register(Player.class);
        kryoServer.register(Player[].class);
        kryoServer.register(Gun.class);
        kryoServer.register(Enemy.class);



        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Register) {

                    for (Player value : players.values()) {
                        connection.sendTCP(value);
                    }

                    for (Enemy enemy1 : enemies) {
                        connection.sendTCP(enemy1);
                    }

                    Player player = new Player();
                    player.id = ((Register) object).id;
                    player.playerType = ((Register) object).playerType;
                    player.x = 800;
                    player.y = 600;
                    player.lives = 500;

                    for (Connection c : players.keySet()) {
                        c.sendTCP(player);
                    }
                    players.put(connection, player);
                }

                if (object instanceof Move) {
                    for (Connection c : players.keySet()) {
                        if (c.equals(connection)) {
                            players.get(c).x = ((Move) object).x;
                            players.get(c).y = ((Move) object).y;
                        }
                        c.sendTCP(object);
                    }
                }

                if (object instanceof Gun) {
                    for (Connection c : players.keySet()) {
                        if (!c.equals(connection)) {
                            c.sendTCP(object);
                        }
                    }
                }

                if (object instanceof LivesLost) {
                    for (Connection c : players.keySet()) {
                        if (c.equals(connection)) {
                            players.get(c).lives = ((LivesLost) object).lives;
                        }
                        c.sendTCP(object);
                    }
                }

            }
        });
    }

    @Override
    public void create() {
        for (Enemy enemy : enemies) {
            
        }
    }
}
