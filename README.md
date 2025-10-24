<img align="right" width="150" height="150" src="https://i.imgur.com/JQhUUdE.png">

### Sistema de Gestión de Tareas “To Do” - Segundo Cuatrimestre UTN FRSN
###### Universidad Tecnológica Nacional - Facultad Regional San Nicolás (UTN FRSN) - Aula Chivilcoy
###### Programación 2 - 2025
###### Profesores: Matías Garro y Gabriel Tonelli

---

### **I. Objetivo**

Desarrollar una aplicación en Java que permita gestionar tareas, **persistiendo los datos en un archivo .csv**. Implementar operaciones CRUD (Alta, Baja, Modificación y Listado) con validaciones esenciales y restricciones de datos.

---

### **II. Entidades y formatos**

#### **2.1 Entidad: Tarea (campos / CSV)**

Se almacenará un archivo `tareas.csv`. Cabecera: `id,titulo,descripcion,fecha_inicio,fecha_vencimiento,estado,prioridad`

**Tipos y ejemplos:**

* `id` — **Numérico `long` único** y auto-incremental. (ej: 123\)  
* `titulo` — Texto (ej: Llamar proveedor)  
* `descripcion` — Texto largo (campos con comas deben ir entre comillas).  
* `fecha_inicio` — Formato `YYYY-MM-DD` (ej: 2025-10-10)  
* `fecha_vencimiento` — Formato `YYYY-MM-DD`  
* `estado` — Enum: `pendiente`, `en progreso`, `completada`  
* `prioridad` — Enum: `alta`, `media`, `baja`

Ejemplo de fila CSV: `1,"Comprar cemento","Comprar 10 bolsas de 50kg","2025-10-01","2025-10-05","pendiente","alta"`

#### **2.2 Enumeraciones**

`Estado`: `pendiente`, `en progreso`, `completada`   
`Prioridad`: `alta`, `media`, `baja`

---

### **III. Requerimientos funcionales (CRUD)**

#### **3.1 Operaciones mínimas**

* **Crear (Alta):** Ingresar datos. El `id` debe generarse automáticamente.  
* **Listar (Read):** Listar todas las tareas.  
* **Actualizar (Modificación):** Modificar cualquier campo **salvo id**. La búsqueda de la tarea a modificar debe ser por su ID.  
* **Eliminar (Baja):** Eliminar la tarea por su ID.

#### **3.2 Reglas y validaciones**

1. **Menús:** Mostrar opciones enumeradas para estados y prioridades, y validar que la entrada sea una opción válida.  
2. **Detección de duplicados:** Antes de crear una tarea, verificar si ya existe otra con el **título exactamente igual** (puede ser *case-insensitive*). Si existe, preguntar confirmación o rechazar la creación.  
3. **Formato de fechas:** Validar formato **`YYYY-MM-DD`**. Validar que `fecha_vencimiento >= fecha_inicio` (si ambas existen).  
4. **Entrada robusta:** Manejar errores de *parsing* del CSV, campos vacíos obligatorios, y opciones numéricas inválidas del usuario.

---

### **IV. Persistencia y Eficiencia (Requisito Clave)**

#### **4.1 Uso del Map para la Gestión en Memoria**

Para garantizar la eficiencia en la gestión de tareas, la aplicación **debe utilizar la interfaz `Map` de Java** para mantener los datos en memoria.

* Al cargar el archivo `tareas.csv`, todos los datos deben ser leídos y almacenados en una colección **`Map<Long, Tarea>`** donde la **clave** sea el `id` de la tarea.  
* Todas las operaciones CRUD (búsqueda, actualización, eliminación por ID) deben hacerse utilizando esta estructura, garantizando un rendimiento óptimo (O(1)).

#### **4.2 Generación de ID**

Debe implementarse un mecanismo (un contador o la búsqueda del ID máximo al cargar) para asegurar que el nuevo `id` asignado a cada tarea creada sea **único y consecutivo** (ej: si el último es 10, el nuevo es 11).

---

### **V. Features 1.1.0 — Reportes**

Implementar las siguientes funcionalidades de listado avanzadas:

1. **Listar Todas las Tareas:** Imprimir la lista completa de tareas.  
2. **Tareas por Estado:** Listar y agrupar las tareas por su `estado` (pendiente, en progreso, completada).  
3. **Tareas de la Semana Actual:** Listar todas las tareas cuya `fecha_vencimiento` caiga dentro de la **semana actual** del sistema (del lunes al domingo o desde día actual a 7 días adelante).

---

### **VI. Ejemplo de interfaz CLI (flujo)**

Al ejecutar mostrar:

Menú:  
1\) Crear tarea  
2\) Listar tareas  
3\) Editar tarea  
4\) Eliminar tarea  
5\) Reportes  
6\) Salir  
Ingrese opcion: \_

---

### **VII. Recomendaciones y buenas prácticas**

* **Estructura:** Organizar en paquetes (entidades, enums, servicios \- que incluirá la lógica del Map y el acceso a CSV).  
* **Persistencia:** Separar la lógica de acceso a CSV del resto del sistema.  
* **Manejo de Fechas:** Usar la API **java.time** para la validación y el reporte de la semana actual.  
* **Git/GitLab/GitHub:** Realizar *commits* frecuentes en ramas.

