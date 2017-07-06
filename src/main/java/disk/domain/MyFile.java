package disk.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Julia
 */
@Entity
@Table(name = "MyFiles")
public class MyFile {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "file_key")
    private String key;

    @Column(name = "file_description")
    private String description;

    @Column(name = "expirationDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    public MyFile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyFile myFile = (MyFile) o;

        if (id != null ? !id.equals(myFile.id) : myFile.id != null) return false;
        if (key != null ? !key.equals(myFile.key) : myFile.key != null) return false;
        if (description != null ? !description.equals(myFile.description) : myFile.description != null) return false;
        return expirationDate != null ? expirationDate.equals(myFile.expirationDate) : myFile.expirationDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }
}
