import java.util.*;

public class AFNSimulator {
    private Set<Integer> states;
    private Set<Character> alphabet;
    private Map<Integer, Map<Character, Set<Integer>>> transitions;
    private Set<Integer> finalStates;

    public AFNSimulator(Set<Integer> states, Set<Character> alphabet, Map<Integer, Map<Character, Set<Integer>>> transitions, Set<Integer> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.finalStates = finalStates;
    }

    public boolean simulate(String input) {
        Set<Integer> currentStates = new HashSet<>();
        currentStates.add(0); // Estado inicial

        for (char symbol : input.toCharArray()) {
            Set<Integer> nextStates = new HashSet<>();

            for (int state : currentStates) {
                Map<Character, Set<Integer>> stateTransitions = transitions.get(state);

                if (stateTransitions != null && stateTransitions.containsKey(symbol)) {
                    nextStates.addAll(stateTransitions.get(symbol));
                }
            }

            currentStates = nextStates;
        }

        // Verifica se algum dos estados finais foi alcançado
        for (int state : currentStates) {
            if (finalStates.contains(state)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // Defina seu AFN
        Set<Integer> states = new HashSet<>(Arrays.asList(0, 1, 2)); // Conjunto de estados: {0, 1, 2}
        Set<Character> alphabet = new HashSet<>(Arrays.asList('0', '1')); // Alfabeto: {0, 1}

        // Defina as transições do AFN
        Map<Integer, Map<Character, Set<Integer>>> transitions = new HashMap<>();
        transitions.put(0, Map.of('0', Set.of(0, 1))); // Transições do estado 0
        transitions.put(1, Map.of('1', Set.of(2))); // Transições do estado 1
        transitions.put(2, Map.of('0', Set.of(2))); // Transições do estado 2

        Set<Integer> finalStates = new HashSet<>(Collections.singletonList(2)); // Estados finais: {2}

        // Crie o simulador do AFN
        AFNSimulator simulator = new AFNSimulator(states, alphabet, transitions, finalStates);

        // Teste algumas entradas
        System.out.println(simulator.simulate("010")); // Saída esperada: true
        System.out.println(simulator.simulate("11")); // Saída esperada: false
        System.out.println(simulator.simulate("000")); // Saída esperada: false
    }
}
