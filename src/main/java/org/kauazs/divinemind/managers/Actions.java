package org.kauazs.divinemind.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Actions {
    Player player;
    private Plugin plugin;
    private Random random = new Random();

    public Actions(Player player, Plugin plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    public void executeAction(String action) {
        switch (action) {
            case "spawn_creeper":
                spawnCreeper();
                break;
            case "clearinventory":
                clearInventory();
                break;
            case "create_explosion":
                createExplosion();
                break;
            case "set_fire":
                setOnFire();
                break;
            case "giveNausea":
                giveNausea();
                break;
            case "giveOneDiamond":
                giveOneDiamond();
                break;
            case "phantom_swarm":
                phantomSwarm();
                break;
            case "trap_in_obsidian":
                trapInObsidian();
                break;
            case "cursed_items":
                giveCursedItems();
                break;
            case "upside_down_world":
                upsideDownWorld();
                break;
            case "fake_diamond":
                fakeDiamondTrap();
                break;
            case "monster_house":
                monsterHouse();
                break;
            case "lava_shower":
                lavaShower();
                break;
            case "inventory_shuffle":
                inventoryShuffle();
                break;
            case "blindness_maze":
                blindnessMaze();
                break;
            case "wither_surprise":
                witherSurprise();
                break;
            case "hunger_games":
                hungerGames();
                break;
            case "fake_death":
                fakeDeath();
                break;
            case "gravity_flip":
                gravityFlip();
                break;
            case "chicken_apocalypse":
                chickenApocalypse();
                break;
            case "tnt_minecart_chase":
                tntMinecartChase();
                break;
        }
    }

    public void clearInventory() {
        player.getInventory().clear();
    }

    public void spawnCreeper() {
        Location location = player.getLocation().clone();
        location.add(0, 0, 2);
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            player.getWorld().spawnEntity(location, EntityType.CREEPER);
        });
    }

    public void createExplosion() {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            player.getWorld().createExplosion(player.getLocation(), 3);
        });
    }

    public void setOnFire() {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            player.setFireTicks(200);
        });
    }

    public void giveOneDiamond() {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
        });
    }

    public void giveNausea() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 15, 2));
        });
    }

    public void phantomSwarm() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Location loc = player.getLocation();
            for (int i = 0; i < 5; i++) {
                Location spawnLoc = loc.clone().add(random.nextInt(10) - 5, 10, random.nextInt(10) - 5);
                player.getWorld().spawnEntity(spawnLoc, EntityType.PHANTOM);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_PHANTOM_AMBIENT, 2.0f, 0.5f);
        });
    }

    public void trapInObsidian() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Location center = player.getLocation();

            for (int x = -1; x <= 1; x++) {
                for (int y = 0; y <= 2; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 1 && z == 0) continue;

                        Block block = center.clone().add(x, y, z).getBlock();
                        block.setType(Material.OBSIDIAN);
                    }
                }
            }

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (int x = -1; x <= 1; x++) {
                    for (int y = 0; y <= 2; y++) {
                        for (int z = -1; z <= 1; z++) {
                            Block block = center.clone().add(x, y, z).getBlock();
                            if (block.getType() == Material.OBSIDIAN) {
                                block.setType(Material.AIR);
                            }
                        }
                    }
                }
            }, 20 * 30);
        });
    }

    public void giveCursedItems() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            ItemStack[] contents = player.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] != null && contents[i].getType() != Material.AIR) {
                    Material original = contents[i].getType();
                    Material cursed = getCursedVersion(original);
                    contents[i] = new ItemStack(cursed, contents[i].getAmount());
                }
            }
            player.getInventory().setContents(contents);
        });
    }

    public void upsideDownWorld() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Location loc = player.getLocation();
            loc.setY(Math.min(loc.getY() + 50, 250));
            player.teleport(loc);

            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 10, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 15, 2));
        });
    }

    public void fakeDiamondTrap() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Location center = player.getLocation();
            for (int i = 0; i < 8; i++) {
                Location diamondLoc = center.clone().add(
                        random.nextInt(6) - 3,
                        0,
                        random.nextInt(6) - 3
                );
                diamondLoc.getBlock().setType(Material.DIAMOND_BLOCK);
            }

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        Block block = center.clone().add(x, 0, z).getBlock();
                        if (block.getType() == Material.DIAMOND_BLOCK) {
                            block.setType(Material.TNT);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                player.getWorld().createExplosion(block.getLocation(), 2);
                            }, 20);
                        }
                    }
                }
            }, 20 * 5);
        });
    }

    public void monsterHouse() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Location center = player.getLocation();

            EntityType[] monsters = {
                    EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER,
                    EntityType.CREEPER, EntityType.WITCH, EntityType.ENDERMAN
            };

            for (int i = 0; i < 10; i++) {
                Location spawnLoc = center.clone().add(
                        random.nextInt(16) - 8,
                        0,
                        random.nextInt(16) - 8
                );

                EntityType monster = monsters[random.nextInt(monsters.length)];
                player.getWorld().spawnEntity(spawnLoc, monster);
            }

            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 0.8f);
        });
    }

    public void lavaShower() {
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 10) {
                    this.cancel();
                    return;
                }

                Location playerLoc = player.getLocation();
                for (int i = 0; i < 5; i++) {
                    Location lavaLoc = playerLoc.clone().add(
                            random.nextInt(10) - 5,
                            10 + random.nextInt(5),
                            random.nextInt(10) - 5
                    );

                    Block block = lavaLoc.getBlock();
                    if (block.getType() == Material.AIR) {
                        block.setType(Material.LAVA);

                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            if (block.getType() == Material.LAVA) {
                                block.setType(Material.AIR);
                            }
                        }, 20 * 10);
                    }
                }
                count++;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void inventoryShuffle() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            ItemStack[] contents = player.getInventory().getContents();

            for (int i = contents.length - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                ItemStack temp = contents[i];
                contents[i] = contents[j];
                contents[j] = temp;
            }

            player.getInventory().setContents(contents);
        });
    }

    public void blindnessMaze() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 30, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 30, 1));

            Location center = player.getLocation();
            for (int x = -5; x <= 5; x++) {
                for (int z = -5; z <= 5; z++) {
                    if (Math.abs(x) == 5 || Math.abs(z) == 5) {
                        for (int y = 0; y < 3; y++) {
                            Block block = center.clone().add(x, y, z).getBlock();
                            block.setType(Material.BARRIER);
                        }
                    }
                }
            }

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (int x = -5; x <= 5; x++) {
                    for (int z = -5; z <= 5; z++) {
                        if (Math.abs(x) == 5 || Math.abs(z) == 5) {
                            for (int y = 0; y < 3; y++) {
                                Block block = center.clone().add(x, y, z).getBlock();
                                if (block.getType() == Material.BARRIER) {
                                    block.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }, 20 * 30);
        });
    }

    public void witherSurprise() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Location loc = player.getLocation().add(0, 5, 0);
            player.getWorld().spawnEntity(loc, EntityType.WITHER);
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 2.0f, 1.0f);
        });
    }

    public void hungerGames() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            player.setFoodLevel(1);
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 60, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 60, 1));
        });
    }

    public void fakeDeath() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 5));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 3, 1));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1.0f, 1.0f);

            Bukkit.broadcastMessage("§4" + player.getName() + " morreu de causas misteriosas!");

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.setHealth(20);
                Bukkit.broadcastMessage("§a" + player.getName() + " ressuscitou magicamente!");
            }, 20 * 5);
        });
    }

    public void gravityFlip() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 8, 3));

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 10, 1));
            }, 20 * 8);
        });
    }

    public void chickenApocalypse() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Location center = player.getLocation();

            for (int i = 0; i < 50; i++) {
                Location chickenLoc = center.clone().add(
                        random.nextInt(20) - 10,
                        random.nextInt(5),
                        random.nextInt(20) - 10
                );
                player.getWorld().spawnEntity(chickenLoc, EntityType.CHICKEN);
            }

            for (int i = 0; i < 10; i++) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_AMBIENT, 2.0f, 1.0f);
                }, i * 10);
            }
        });
    }

    public void tntMinecartChase() {
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 5) {
                    this.cancel();
                    return;
                }

                Location behind = player.getLocation().subtract(player.getLocation().getDirection().multiply(3));
                player.getWorld().spawnEntity(behind, EntityType.TNT);

                player.playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, 1.0f, 1.2f);

                count++;
            }
        }.runTaskTimer(plugin, 0, 20 * 2);
    }

    private Material getCursedVersion(Material original) {
        switch (original) {
            case DIAMOND_SWORD: return Material.WOODEN_SWORD;
            case DIAMOND_PICKAXE: return Material.WOODEN_PICKAXE;
            case DIAMOND_AXE: return Material.WOODEN_AXE;
            case DIAMOND_SHOVEL: return Material.WOODEN_SHOVEL;
            case DIAMOND_HOE: return Material.WOODEN_HOE;
            case DIAMOND: return Material.COAL;
            case GOLD_INGOT: return Material.IRON_INGOT;
            case IRON_INGOT: return Material.COBBLESTONE;
            case COOKED_BEEF: return Material.ROTTEN_FLESH;
            case BREAD: return Material.POISONOUS_POTATO;
            default: return Material.DIRT;
        }
    }
}