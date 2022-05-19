/* IMPORTA O MÓDULO DO SEQUELIZE */
import { Sequelize } from "sequelize";

const connection = new Sequelize('libri_upload', 'root', '12345678', {
    host: 'localhost',
    dialect: 'mysql',
    timezone: '-03:00'
});

export default { connection }; 