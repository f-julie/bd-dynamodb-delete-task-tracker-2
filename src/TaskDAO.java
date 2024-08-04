
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;

import java.util.HashMap;
import java.util.Map;

public class TaskDAO {

    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Task objects from the data store.
     * @param mapper Access to DynamoDB
     */
    public TaskDAO(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Sets a condition that an item is only deleted if 'year_completed' = '2016', otherwise the item is not deleted.
     * @param task Task instance that is passed into the delete() method to delete if it meets the delete condition
     */
    public void deleteTask(Task task) {
        //TODO: Replace this code to add the delete condition described above and in the README.
        try {
            DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression();
            Map<String, ExpectedAttributeValue> expected = new HashMap<>();
            expected.put("year_completed", new ExpectedAttributeValue(new AttributeValue().withN("2016")));
            deleteExpression.setExpected(expected);
            mapper.delete(task, deleteExpression);
        } catch (ConditionalCheckFailedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses the load() method to get an item from the TaskTracker table given a partition key value.
     * @param partitionKey the given partition key value used to find the correct item to load.
     * @return the Task instance that's been loaded from the table
     */
    public Task getTask(String partitionKey) {
        Task task = mapper.load(Task.class, partitionKey);
        return task;
    }

    /**
     * Uses the save() method to save a given item to the TaskTracker table.
     * @param task given Task instance that's saved to the table
     */
    public void saveTask(Task task) {
        mapper.save(task);
    }

}
