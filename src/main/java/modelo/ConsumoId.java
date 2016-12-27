package modelo;
// Generated 23/12/2016 10:43:28 AM by Hibernate Tools 4.3.1



/**
 * ConsumoId generated by hbm2java
 */
public class ConsumoId  implements java.io.Serializable {


     private int id;
     private String tarjeta;

    public ConsumoId() {
    }

    public ConsumoId(int id, String tarjeta) {
       this.id = id;
       this.tarjeta = tarjeta;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getTarjeta() {
        return this.tarjeta;
    }
    
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ConsumoId) ) return false;
		 ConsumoId castOther = ( ConsumoId ) other; 
         
		 return (this.getId()==castOther.getId())
 && ( (this.getTarjeta()==castOther.getTarjeta()) || ( this.getTarjeta()!=null && castOther.getTarjeta()!=null && this.getTarjeta().equals(castOther.getTarjeta()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getId();
         result = 37 * result + ( getTarjeta() == null ? 0 : this.getTarjeta().hashCode() );
         return result;
   }   


}

