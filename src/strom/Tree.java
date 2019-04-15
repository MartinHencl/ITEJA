package strom;

public class Tree <T> {

    public class Node {
        private T key;
        //Node[] branches;
        private Node leftBranch;
        private Node rightBranch;

        public Node() {
            this.key = null;
            this.leftBranch = null;
            this.rightBranch = null;
        }

        public Node(T data) {
            this.key = data;
            this.leftBranch = null;
            this.rightBranch = null;
        }

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        private Node getLeftBranch() {
            return leftBranch;
        }

        private void setLeftBranch(Node leftBranch) {
            this.leftBranch = leftBranch;
        }

        private Node getRightBranch() {
            return rightBranch;
        }

        private void setRightBranch(Node rightBranch) {
            this.rightBranch = rightBranch;
        }

        private boolean checkEmptyBothBranches() {
            return leftBranch == null || rightBranch == null;
        }

        private boolean checkEmptyLeftBranch() {
            return leftBranch == null;
        }

        private boolean checkEmptyRightBranch() {
            return rightBranch == null;
        }

    }

    private Node rootNode;
    private Node actualNode;

    public Tree() {
        rootNode = null;
        actualNode = null;
    }

    public Tree(T data) {
        rootNode = new Node(data);
        actualNode = rootNode;
    }

    public void add(T data, String branch) {
        Node tempNode = new Node(data);
        if (rootNode == null) {
            rootNode = tempNode;
            actualNode = rootNode;
        } else {
            addToBranch(tempNode, branch);
        }
    }

    private void addToBranch(Node insertingNode, String branch) {
        if (branch.equals("left")) {
            this.actualNode.setLeftBranch(insertingNode);
        } else {
            this.actualNode.setRightBranch(insertingNode);
        }
    }

    public boolean setActiveNodeFromRightNode() {
        if (!actualNode.checkEmptyRightBranch()) {
            actualNode = actualNode.getRightBranch();
            return true;
        }
        return false;
    }
}
