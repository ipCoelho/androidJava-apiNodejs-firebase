import { connection } from "../../database/database";
import { Sequelize } from 'sequelize';

const Livro = connection.define(
    'tbl_livro',
    {
        cod_livro:{
            type: Sequelize.INTEGER(10),
            primaryKey: true,
            autoIncrement: true
        },
        titulo:{
            type: Sequelize.STRING(200),
            allowNull: true
        },
        imagem:{
            type: Sequelize.STRING(500),
            allowNull: true
        }
    }
);

Livro.sync({force:true});

export default { Livro };