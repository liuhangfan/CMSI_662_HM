const bigInt = require("big-integer");
const crypto = require('crypto');

function calculateN(p, q) {
    return p.multiply(q);
}

function calculateD(p, q, e){
    const phiN = p.minus(1).multiply(q.minus(1));
    const d = e.modInv(phiN);
    return d;
}

function textBlockToBigInt(textBlock) {
    const buffer = Buffer.from(textBlock, 'utf8');
    return bigInt(buffer.toString('hex'), 16);
}

function encryptBlock(blockBigInt, e, N) {
    return blockBigInt.modPow(e, N).toString(16);
}

function applyPKCS7Padding(buffer, blockSize) {
    const paddingNeeded = blockSize - (buffer.length % blockSize);
    const padding = Buffer.alloc(paddingNeeded, paddingNeeded);
    return Buffer.concat([buffer, padding]);
}

function encryptTextWithBlocks(text, blockSize, e, N) {
    let encryptedBlocks = [];
    let buffer = Buffer.from(text, 'utf8');
    buffer = applyPKCS7Padding(buffer, blockSize);
    for (let i = 0; i < buffer.length; i += blockSize) {
        const block = buffer.slice(i, i + blockSize);
        const blockBigInt = bigInt(block.toString('hex'), 16);
        const encryptedBlock = encryptBlock(blockBigInt, e, N);
        encryptedBlocks.push(encryptedBlock);
    }

    return encryptedBlocks;
}


function decryptBlock(encryptedBigInt, d, N) {
    return encryptedBigInt.modPow(d, N).toString(16);
}

function removePKCS7Padding(buffer) {
    const paddingLength = buffer[buffer.length - 1];
    return buffer.slice(0, buffer.length - paddingLength);
}

function decryptTextWithBlocks(encryptedBlocks, d, N) {
    let decryptedMessage = Buffer.alloc(0);
    encryptedBlocks.forEach(encryptedBlock => {
        const blockBigInt = bigInt(encryptedBlock, 16);
        const decryptedHex = decryptBlock(blockBigInt, d, N);
        let decryptedBlock = Buffer.from(decryptedHex, 'hex');
        decryptedMessage = Buffer.concat([decryptedMessage, decryptedBlock]);
    });
    decryptedMessage = removePKCS7Padding(decryptedMessage);
    return decryptedMessage.toString('utf8');
}


const p = bigInt("100392089237316158323570985008687907853269981005640569039457584007913129640081");
const q = bigInt("90392089237316158323570985008687907853269981005640569039457584007913129640041");
const e = bigInt("65537");
const N = calculateN(p,q);
const d = calculateD(p,q,e);
console.log(`N = ${N.toString()}`);
console.log(`d = ${d.toString()}`);

// Encrypt
const message = "Scaramouche, Scaramouche, will you do the Fandango? 💃🏽";
console.log("message:", message);
const blockSize = 60;
const ciphertext = encryptTextWithBlocks(message, blockSize, e, N);
console.log("Encrypted ciphertext:", ciphertext);

// Decrypt
const decryptedMessage = decryptTextWithBlocks(ciphertext, d, N);
console.log("Decrypted message:", decryptedMessage);