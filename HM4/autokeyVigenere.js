function autokeyVigenereEncryptWithCiphertext(plaintext, keyphrase) {
    const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    let key = keyphrase;
    let ciphertext = "";

    for (let i = 0; i < plaintext.length; i++) {
        const char = plaintext[i];
        if (alphabet.includes(char)) {
            const pIndex = alphabet.indexOf(char);
            const kIndex = alphabet.indexOf(key[i]);
            const cIndex = (pIndex + kIndex) % 26;
            const cChar = alphabet[cIndex];
            ciphertext += cChar;
            key += cChar;
        } else {
            ciphertext += char;
        }
    }

    return ciphertext;
}

const plaintext = "TAKEACOPYOFYOURPOLICYTONORMAWILCOXONTHETHIRDFLOOR";
const keyphrase = "QUARK";
const ciphertext = autokeyVigenereEncryptWithCiphertext(plaintext, keyphrase);
console.log("plaintext:", plaintext);
console.log("keyphrase:", keyphrase);
console.log("ciphertext:", ciphertext);
