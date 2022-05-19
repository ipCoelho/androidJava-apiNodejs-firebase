import express from "express";
import fs from "fs";
import { Livro } from "./database/model/Livro";

async function API() {
    const api = express(), port = 3000, host = "http://localhost";

    api.use(express.json())
        .use(express.urlencoded({
            extended: true,
            limit: '50mb'
        }));
    
    api.post("/upload", (req, res) => {
        const imgBase64 = req.body.img;
        const buffer = new Buffer.from(imgBase64, "base64");
        const path =  `./src/upload/image_${Date.now()}.jpg`;

        /*
         * arg1 = the path that the file is going to be written.
         * arg2 = the variable with the reference of where is the buffer in the memory.
         * arg3 = the encoded method.
         * arg4 = a cb fun.
         */
        fs.writeFileSync(path, buffer, error => {
            error? console.log(`filesystem error: ${error}`): '';
        });

        Livro.create({
            titulo: req.body.titulo,
            imagem: path
        });

        if (req.body != null) res.status(200);
    });
    
    api.listen(port, () => console.log(`Service running at ${host}:${port}`));
}

API();