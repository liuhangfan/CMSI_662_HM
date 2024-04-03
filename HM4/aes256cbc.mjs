import crypto from 'crypto';

function encrypt(text, key, vector) {
    const cipher = crypto.createCipheriv('aes-256-cbc', Buffer.from(key), vector);
    let res = cipher.update(text, 'utf8', 'hex');
    res += cipher.final('hex');
    return res;
}

function decrypt(encrypted, key, vector) {
    const decipher = crypto.createDecipheriv('aes-256-cbc', Buffer.from(key), vector);
    let res = decipher.update(encrypted, 'hex', 'utf8');
    res += decipher.final('utf8');
    return res;
}

function main() {
    const [operation, input, key, vector] = process.argv.slice(2);
    if (key.length !== 32) {
        console.error('The key must be 32 bytes long.');
        process.exit(1);
    }
    if (vector.length !== 16) {
        console.error('The initialization vector must be 16 bytes long.');
        process.exit(1);
    }

    if (operation === '-e') {
        console.log(encrypt(input, key, vector));
    } else if (operation === '-d') {
        console.log(decrypt(input, key, vector));
    } else {
        console.error('Invalid operation. Use -e for encrypt or -d for decrypt.');
        process.exit(1);
    }
}

main();
