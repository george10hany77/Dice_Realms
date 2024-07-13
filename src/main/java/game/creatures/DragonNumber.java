package game.creatures;

import game.exceptions.InvalidDragonNumber;

public enum DragonNumber {
    Dragon1("Dragon 1"),Dragon2("Dragon 2"),Dragon3("Dragon 3"),Dragon4("Dragon 4");
    private String name;

    private DragonNumber(String name) {
        this.name = name;
    }

    public static DragonNumber getDragon(int i) throws InvalidDragonNumber {
        switch (i){
            case 1:
                return Dragon1;
            case 2:
                return Dragon2;
            case 3:
                return Dragon3;
            case 4:
                return Dragon4;
        }
        throw new InvalidDragonNumber("Dragon number is invalid !");
    }

    public String getName() {
        return name;
        }
}

