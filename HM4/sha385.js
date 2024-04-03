const crypto = require('crypto');

function calculateSHA384(text) {
    return crypto.createHash('sha384').update(text).digest('hex');
}

const text = "Російський військовий корабель, іди нахуй";
const sha384Hash = calculateSHA384(text);

console.log(`SHA-384 hash of "${text}":`, sha384Hash);
