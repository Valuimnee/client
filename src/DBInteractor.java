import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface.
 * @author Calapova Maria
 * @version 1.1, 3/23/2017
 */
public interface DBInteractor extends Remote {
    /**
     * This method gets information about all not passed students.
     * @return Information about not passed students.
     * @throws RemoteException if <a href="https://docs.oracle.com/javase/tutorial/rmi/"> RMI</a> error occurs.
     * @see DBResponse
     */

    DBResponse getNotPassedStudents() throws RemoteException;

    /**
     * This method gets information about all average students.
     * @return Information about average students.
     * @throws RemoteException if RMI error occurs.
     * @see DBResponse
     */
    DBResponse getAverageStudents() throws RemoteException;

    /**
     * This method gets information about all excellent students.
     * @return Information about excellent students.
     * @throws RemoteException if RMI error occurs.
     * @see DBResponse
     */
    DBResponse getExcellentStudents() throws RemoteException;

    /**
     * This method gets statistics about all students.
     * @return Statistics about all students.
     * @throws RemoteException if RMI error occurs.
     * @see DBResponse
     */
    DBResponse getStatistics() throws RemoteException;

    /**
     * This method gets information about all students.
     * @return Information about all students.
     * @throws RemoteException if RMI error occurs.
     * @see DBResponse
     */
    DBResponse getAllStudents() throws RemoteException;

    /**
     * This method gets information about all exams.
     * @return Information about each exam(<i>name</i>).
     * @throws RemoteException if RMI error occurs.
     * @see DBResponse
     */
    DBResponse getExams() throws RemoteException;

    /**
     * Adds a student to the database.
     * @param request All information about student(e.g. <i>full name, group, marks</i>
     * @throws RemoteException if RMI error occurs.
     * @see DBRequest
     */
    void addStudent(DBRequest request) throws RemoteException;

    /**
     * Gets all information about certain student.
     * @param request Information about student.
     * @return {@link DBResponse} Returns all information about student.
     * @throws RemoteException if RMI error occurs.
     * @see DBRequest
     * @see DBResponse
     */
    DBResponse getStudentInformation(DBRequest request) throws RemoteException;

    /**
     * Edit information about certain student.
     * @param request Information about student(old and new).
     * @throws RemoteException if RMI error occurs.
     * @see DBRequest
     */
    void editStudentInformation(DBRequest request) throws RemoteException;

    /**
     * Deletes certain student.<br>If there is no such student, returns appropriate message.
     * @param request Information about the student.
     * @return Answer specifying whether student was deleted or not.
     * @throws RemoteException if RMI error occurs.
     * @see DBRequest
     * @see DBResponse
     */
    DBResponse deleteStudent(DBRequest request) throws RemoteException;
}