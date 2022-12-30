package by.homework.hlazarseni.binlistapp.database
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BinEntity(
    @PrimaryKey
    @NonNull
    val name: String
)
