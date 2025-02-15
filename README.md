Spring Boot

Proyecto usando JUnit 5 para realizar test a una base de datos real:
- Test 1 -> Crear registro
- Test 2 -> Buscar por nombre
- Test 3 -> Buscar por nombre no existente
- Test 4 -> Actualizar registro
- Test 5 -> Listar todos los registros
- Test 6 -> Eliminar registro

He usado @Rollback para trabajar de forma real en la base de datos, y le he puesto un orden a los @Test para controlar el flujo de estos.
