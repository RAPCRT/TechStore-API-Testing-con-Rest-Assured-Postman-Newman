# TechStore API

API de demostración para gestionar productos y categorías en una tienda de tecnología.

## Cómo ejecutar el proyecto

1.  **Clona el repositorio:**
    ```bash
    git clone https://github.com/tu-usuario/api-base.git
    cd api-base
    ```
2.  **Compila y ejecuta el proyecto usando Maven:**
    ```bash
    ./mvnw spring-boot:run
    ```
    La API estará disponible en `http://localhost:8080`.

## Endpoints de la API

La URL base de la API es `http://localhost:8080/api/v1`.

### Categorías

| Método | Endpoint | Descripción | Body (Ejemplo) |
| --- | --- | --- | --- |
| `GET` | `/categories` | Obtiene todas las categorías. | |
| `POST` | `/categories` | Crea una nueva categoría. | `{"nombre": "Laptops", "descripcion": "Portátiles de alto rendimiento"}` |
| `GET` | `/categories/{id}` | Obtiene una categoría por su ID. | |
| `PUT` | `/categories/{id}` | Actualiza una categoría existente. | `{"nombre": "Laptops Gaming", "descripcion": "Portátiles optimizados para juegos"}` |
| `DELETE` | `/categories/{id}` | Elimina una categoría. | |
| `GET` | `/categories/{id}/products` | Obtiene todos los productos de una categoría. | |

### Productos

| Método | Endpoint | Descripción | Body (Ejemplo) |
| --- | --- | --- | --- |
| `GET` | `/products` | Obtiene todos los productos. | |
| `POST` | `/products` | Crea un nuevo producto. | `{"nombre": "Laptop Pro", "descripcion": "Laptop para profesionales", "precio": 1500.00, "stock": 50, "id_categoria": 1}` |
| `GET` | `/products/{id}` | Obtiene un producto por su ID. | |
| `PUT` | `/products/{id}` | Actualiza un producto existente. | `{"nombre": "Laptop Pro X", "descripcion": "Versión mejorada", "precio": 1600.00, "stock": 40, "id_categoria": 1}` |
| `PATCH` | `/products/{id}` | Actualiza parcialmente un producto. | `{"precio": 1550.00}` |
| `DELETE` | `/products/{id}` | Elimina un producto. | |

## Cómo usar con Postman

1.  **Inicia la aplicación** como se describe en "Cómo ejecutar el proyecto".
2.  **Abre Postman** y crea una nueva colección.

### Crear una Categoría (POST)

*   **URL:** `POST http://localhost:8080/api/v1/categories`
*   **Body:** `raw` > `JSON`
    ```json
    {
        "nombre": "Smartphones",
        "descripcion": "Teléfonos inteligentes de última generación"
    }
    ```
*   Haz clic en **Send**. Deberías ver la nueva categoría en la respuesta con un código `201 Created`.

### Crear un Producto (POST)

*   **URL:** `POST http://localhost:8080/api/v1/products`
*   **Body:** `raw` > `JSON`
    ```json
    {
        "nombre": "Smartphone Z",
        "descripcion": "El último modelo con cámara IA",
        "precio": 999.99,
        "stock": 100,
        "id_categoria": 2 
    }
    ```
*   Haz clic en **Send**.

### Obtener todos los Productos (GET)

*   **URL:** `GET http://localhost:8080/api/v1/products`
*   Haz clic en **Send**. Verás una lista de todos los productos.

### Importar la Colección de Postman

Puedes importar la siguiente colección para tener todos los endpoints listos para usar:

```json
{
    "info": {
        "_postman_id": "...",
        "name": "TechStore API",
        "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
    },
    "item": [
        {
            "name": "Categories",
            "item": [
                { "name": "Get All", "request": { "method": "GET", "url": { "raw": "{{baseUrl}}/categories" } } },
                { "name": "Create", "request": { "method": "POST", "url": { "raw": "{{baseUrl}}/categories" }, "body": { "mode": "raw", "raw": "{
    "nombre": "Keyboards",
    "descripcion": "Mechanical and membrane keyboards"
}" } } }
            ]
        },
        {
            "name": "Products",
            "item": [
                { "name": "Get All", "request": { "method": "GET", "url": { "raw": "{{baseUrl}}/products" } } },
                { "name": "Create", "request": { "method": "POST", "url": { "raw": "{{baseUrl}}/products" }, "body": { "mode": "raw", "raw": "{
    "nombre": "Mechanical Keyboard",
    "descripcion": "RGB Mechanical Keyboard",
    "precio": 120.00,
    "stock": 75,
    "id_categoria": 3
}" } } }
            ]
        }
    ],
    "variable": [
        {
            "key": "baseUrl",
            "value": "http://localhost:8080/api/v1"
        }
    ]
}
```
**Para importar:**
1.  En Postman, haz clic en `Import`.
2.  Pega el JSON anterior en la pestaña `Raw text`.
3.  Haz clic en `Continue` e `Import`.
