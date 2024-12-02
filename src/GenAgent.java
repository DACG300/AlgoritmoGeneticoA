import jade.core.Agent;


public class GenAgent extends Agent {
    @Override
    public void setup() {
        addBehaviour(new GenBehaviour());
    }
}


