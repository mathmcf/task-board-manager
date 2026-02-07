package br.com.service;

import br.com.persistence.dao.BoardColumnDAO;
import br.com.persistence.dao.BoardDAO;
import br.com.persistence.entity.BoardColumnEntity;
import br.com.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.persistence.entity.BoardColumnKindEnum.findByName;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException{
        var dao = new BoardDAO(connection);
        var optional = dao.findById(id);
        var boardColumnDAO = new BoardColumnDAO(connection);

        if(optional.isPresent()){
            var entity = optional.get();
            entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));
        }

        return Optional.empty();
    }

    public void showBoardDetails(final Long id) throws SQLException{

        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);

        if(optional.isPresent()){
            var entity = optional.get();
            entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));

        }
    }

    public List<BoardColumnEntity> findByBoardId(final Long id) throws SQLException {

        List<BoardColumnEntity> entities = new ArrayList<>();

        var sql = "SELECT FROM BOARDS_COLUMNS WHERE board_id = ? ORDER BY `order`;";
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1,id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while(resultSet.next()){
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
        }
        return entities;
    }

}
