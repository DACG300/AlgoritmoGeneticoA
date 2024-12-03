import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class GenAgent extends Agent {

    @Override
    public void setup() {
        System.out.println("El agente GenAgent está listo.");
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ejecutarOperacion();
            }
        });
    }

    public void ejecutarOperacion() {
        System.out.println("Ejecutando operación.");
        addBehaviour(new GenBehaviour());
        addBehaviour(new GenBehaviour() {
            @Override
            public void action() {
                System.out.println("Operación finalizada.");
                //myAgent.doDelete();
            }
        });
    }

    @Override
    public void takeDown() {
        System.out.println("El agente GenAgent terminó.");
    }
}
