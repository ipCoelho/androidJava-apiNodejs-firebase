import express from "express";
import fs from "fs";

const api = express(), port = 3000, host = "http://localhost";

async function API() {
    api.use(express.json())
        .use(express.urlencoded({
            extended: true,
            limit: '5MB'
        }));
    
    api.post("/upload", (req, res) => {
        const buffer = new Buffer.from(req.body.img, "base64");
        const path = `./upload/img-${new Date().toLocaleString.toString()}/.jpg`;

        /**
         * arg1 = the path that the file is going to be written.
         * arg2 = the variable with the reference of where is the buffer in the memory.
         * arg3 = the encoded method.
         * arg4 = a cb fun.
         */
        fs.writeFileSync(path, buffer, 'base64', error => {
            error? console.log(`Error on fs: `, error): '';
        });
    });
    
    api.listen(port, () => {
        console.log(`Service running at ${host}:${port}`);
    })
}