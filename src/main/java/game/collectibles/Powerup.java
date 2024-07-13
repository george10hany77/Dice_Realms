package game.collectibles;

abstract public class Powerup {
    private PowerUpState state;

    public Powerup() {
        state = PowerUpState.DISABLED;
    }

    public boolean Enable() {
        if (state == PowerUpState.DISABLED)
            state = PowerUpState.ENABLED;
        else
            return false;
        return true;
    }
    public boolean use()
    {
        if(state == PowerUpState.ENABLED)
            {
                state = PowerUpState.USED;
                return true;
            }
        else
        {
            return false;
        }
    }

    public PowerUpState getPowerUpState() {
        return state;
    }

    public void setPowerUpState(PowerUpState state) {
        this.state = state;
    }

}
