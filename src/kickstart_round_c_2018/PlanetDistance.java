package kickstart_round_c_2018;

import util.InputData;
import util.OutputData;
import util.ProblemDataIO;

import java.util.*;

public class PlanetDistance {
    static class PlanetDistance_StringsInputData implements InputData {
        List<ArrayList<Integer>[]> data;

        @Override
        public void setParams(String[] params) {
            data = new ArrayList<>(Integer.valueOf(params[0]));
        }

        private ArrayList<Integer>[] graph;
        private int size;
        @Override
        public void fillData(String dataLine) {
            if (size == 0) {
                size = Integer.valueOf(dataLine.split(" ")[0]);
                graph = new ArrayList[size];
                data.add(graph);
                for(int i = 0; i < size; i++) {
                    graph[i] = new ArrayList<>();
                }
            } else {
                size--;
                String[] line = dataLine.split(" ");
                Integer x = Integer.valueOf(line[0]) - 1;
                Integer y = Integer.valueOf(line[1]) - 1;
                graph[x].add(y);
                graph[y].add(x);
            }
        }
    }

    static class PlanetDistance_StringsOutputData implements OutputData {

        @Override
        public String getOutput(Object data) {
            List<List<Integer>> strs = (List<List<Integer>>) data;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < strs.size(); ++i) {
                builder.append("Case #").append(i + 1).append(":");
                for (Integer n : strs.get(i)) {
                    builder.append(" ").append(n);
                }
                builder.append("\n");
            }
            return builder.toString();
        }
    }

    public static void main(String... args) {
        String[] inputs = {"A-large-practice.in"};
        for (String in : inputs) {
            ProblemDataIO dataIO = new ProblemDataIO(new PlanetDistance_StringsInputData(),
                    new PlanetDistance_StringsOutputData(),
                    "kickstart_round_c_2018", in);
            PlanetDistance_StringsInputData inputData = (PlanetDistance_StringsInputData) dataIO.readData();
            List<List<Integer>> numbs = getAnswer(inputData.data);
            dataIO.writeData(numbs);
        }
    }

    private static List<List<Integer>> getAnswer(List<ArrayList<Integer>[]> data) {
        List<List<Integer>> result = new ArrayList<>(data.size());
        for (ArrayList<Integer>[] graph : data) {
            result.add(getPlanetsDistance(graph));
        }
        return result;
    }

    private static List<Integer> getPlanetsDistance(ArrayList<Integer>[] graph) {
        List<Integer> cycle = getCycle(graph);
        return getDistance(cycle, graph);
    }

    private static List<Integer> getDistance(List<Integer> cycle, ArrayList<Integer>[] graph) {
        List<Integer> distances = new ArrayList<>(graph.length);
        for (int i = 0; i < graph.length; ++i) {
            distances.add(0);
        }
        for (int i = 0; i < graph.length; ++i) {
            if (!cycle.contains(i)) {
                distances.set(i, getMinDistance(i, cycle, graph));
            }
        }
        return distances;
    }

    private static Integer getMinDistance(Integer node, List<Integer> cycle, List<Integer>[] graph) {
        int min = Integer.MAX_VALUE;
        for (Integer cycleNode : cycle) {
            int currMin = minEdgeBFS(graph, node, cycleNode, graph.length);
            if (min > currMin) {
                min = currMin;
            }
        }
        return min;
    }

    static int minEdgeBFS(List<Integer>[] edges, int u, int v, int n) {
        // visited[n] for keeping track of visited
        // node in BFS
        Vector<Boolean> visited = new Vector<Boolean>(n);

        for (int i = 0; i < n; i++) {
            visited.addElement(false);
        }

        // Initialize distances as 0
        Vector<Integer> distance = new Vector<Integer>(n);

        for (int i = 0; i < n; i++) {
            distance.addElement(0);
        }

        // queue to do BFS.
        Queue<Integer> Q = new LinkedList<>();
        distance.setElementAt(0, u);

        Q.add(u);
        visited.setElementAt(true, u);
        while (!Q.isEmpty())
        {
            int x = Q.peek();
            Q.poll();

            for (int i=0; i<edges[x].size(); i++)
            {
                if (visited.elementAt(edges[x].get(i)))
                    continue;

                // update distance for i
                distance.setElementAt(distance.get(x) + 1,edges[x].get(i));
                Q.add(edges[x].get(i));
                visited.setElementAt(true,edges[x].get(i));
            }
        }
        return distance.get(v);
    }

    private static List<Integer> getCycle(ArrayList<Integer>[] graph) {
        Integer cycleNode = isCyclic(graph, graph.length);
        return getCycleNodes(cycleNode, graph);
    }

    private static List<Integer> getCycleNodes(Integer parentNode, ArrayList<Integer>[] graph) {
        List<Integer> maxPath = new LinkedList<>();
        Queue<LinkedList<Integer>> q = new LinkedList<>();
        for (Integer planet : graph[parentNode]) {
            LinkedList<Integer> newPath = new LinkedList<>();
            newPath.addFirst(planet);
            q.add(newPath);
        }

        while (!q.isEmpty()) {
            LinkedList<Integer> path = ((LinkedList<LinkedList<Integer>>) q).pop();
            Integer node = path.getFirst();
            if (node.equals(parentNode) && path.size() != 1) {
                if (maxPath.size() < path.size()) {
                    maxPath = path;
                }
            }
            for (Integer planet : graph[node]) {
                if (!path.contains(planet)) {
                    LinkedList<Integer> newPath = new LinkedList<>(path);
                    newPath.addFirst(planet);
                    q.add(newPath);
                }
            }
        }
        return maxPath;
    }

    private static Integer getCyclicNode(int v, Boolean visited[], int parent, List<Integer> adj[]) {
        // Mark the current node as visited
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext())
        {
            i = it.next();

            // If an adjacent is not visited, then recur for that
            // adjacent
            if (!visited[i])
            {
                Integer node = getCyclicNode(i, visited, v, adj);
                if (node != null)
                    return node;
            }

            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            else if (i != parent)
                return i;
        }
        return null;
    }

    // Returns true if the graph contains a cycle, else false.
    private static Integer isCyclic(List<Integer> adj[], int V) {
        // Mark all the vertices as not visited and not part of
        // recursion stack
        Boolean visited[] = new Boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Call the recursive helper function to detect cycle in
        // different DFS trees
        for (int u = 0; u < V; u++)
            if (!visited[u]) { // Don't recur for u if already visited
                Integer cycleNode = getCyclicNode(u, visited, -1, adj);
                if (cycleNode != null)
                    return cycleNode;
            }

        return null;
    }
}
