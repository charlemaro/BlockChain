package BlockChain;

import java.util.ArrayList;
import java.util.Date;

class Block {
    public int index;
    public String previousHash;
    public String data;
    public long timestamp;
    public String hash;

    // Constructor to initialize a new Block
    public Block(int index, String previousHash, String data) {
        this.index = index;
        this.previousHash = previousHash;
        this.data = data;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    // Method to calculate the hash of the block using simple string concatenation
    public String calculateHash() {
        String input = index + previousHash + Long.toString(timestamp) + data;
        return Integer.toHexString(input.hashCode());
    }
}

class Blockchain {
    public ArrayList<Block> chain;

    // Constructor to initialize the blockchain with the first block (genesis block)
    public Blockchain() {
        chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }

    // Method to create the first block (genesis block)
    private Block createGenesisBlock() {
        return new Block(0, "0", "Genesis Block");
    }

    // Method to get the latest block in the blockchain
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    // Method to add a new block to the blockchain
    public void addBlock(String data) {
        Block previousBlock = getLatestBlock();
        Block newBlock = new Block(previousBlock.index + 1, previousBlock.hash, data);
        chain.add(newBlock);
    }

    // Method to check if the blockchain is valid by verifying each block's hash and the previousHash
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Check if the current block's hash is correct
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                return false;
            }

            // Check if the previous block's hash is correctly stored in the current block
            if (!currentBlock.previousHash.equals(previousBlock.hash)) {
                return false;
            }
        }
        return true;
    }
}

public class JavaBlockChain {
    public static void main(String[] args) {
        // Create a new Blockchain
        Blockchain blockchain = new Blockchain();

        // Add blocks with some data
        blockchain.addBlock("First block data");
        blockchain.addBlock("Second block data");

        // Display each block in the blockchain
        for (Block block : blockchain.chain) {
            System.out.println("Block " + block.index + " :");
            System.out.println("Previous Hash: " + block.previousHash);
            System.out.println("Data: " + block.data);
            System.out.println("Hash: " + block.hash);
            System.out.println("Timestamp: " + block.timestamp);
            System.out.println();
        }

        // Validate the blockchain
        System.out.println("Blockchain is valid: " + blockchain.isChainValid());
    }
}

