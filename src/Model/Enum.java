package Model;

public class Enum {
    public enum EventState
    {
        BEING_HANDLED, DONE
    }
    public enum Organization
    {
        HOTLINE;

        public enum SecurityForces
        {
            POLICE, REDCROSS, FIREMEN
        }
    }
}
