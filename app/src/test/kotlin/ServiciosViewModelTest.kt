//package com.example.artedigitalapp.viewmodel
//
//import com.example.artedigitalapp.models.Servicio
//import com.example.artedigitalapp.repository.ServicioRepository
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import java.lang.RuntimeException
//
//// Nota: Este código de prueba asume que estás utilizando la librería Kotest (FunSpec, shouldBe)
//// y la librería Mockk (mockk, coEvery).
//
//// 🛠️ Setup: Usamos TestDispatcher para controlar las corrutinas
//@OptIn(ExperimentalCoroutinesApi::class)
//class ServiciosViewModelTest : FunSpec({
//
//    // Regla para manejar los Dispatchers de las corrutinas
//    // Se usa StandardTestDispatcher para ejecutar las corrutinas de manera síncrona en el test
//    val testDispatcher = StandardTestDispatcher()
//    Dispatchers.setMain(testDispatcher) // Redirige el Dispatchers.Main a nuestro dispatcher de prueba
//
//    // 1. Preparación de Objetos (Arrange Global)
//
//    // Creamos un Mock (simulación) del Repositorio. 'relaxed = true' simula respuestas por defecto
//    val mockRepository: ServicioRepository = mockk(relaxed = true)
//
//    // Creamos una instancia del ViewModel, inyectándole el repositorio simulado.
//    // Recordatorio: El constructor de ServiciosViewModel debe aceptar un ServicioRepository.
//    val viewModel = ServiciosViewModel(mockRepository)
//
//    // Definimos datos falsos (múltiples servicios) para usar en varios tests
//    val mockServicios = listOf(
//        Servicio(
//            id = 1L,
//            nombre = "Arte Mock",
//            precio = 100.0,
//            descripcion = "Desc",
//            activo = true
//        ),
//    )
//
//    // --- PRUEBA 1: listarServicios - Éxito ---
//    test("listarServicios debe cargar la lista de servicios y limpiar el error") {
//        runTest { // Bloque de prueba de corrutinas para manejar el flujo asíncrono
//            // ARRANGE: Configurar el mock para devolver los datos falsos al ser llamado
//            coEvery { mockRepository.obtenerServiciosBackend() } returns mockServicios
//
//            // ACT: Llamar a la función del ViewModel que se va a probar
//            viewModel.listarServicios()
//
//            // Ejecutar corrutina interna del ViewModel (permite que la corrutina se complete)
//            testScheduler.runCurrent()
//
//            // ASSERT: Verificar que el StateFlow 'servicios' se haya actualizado con los datos esperados
//            viewModel.servicios.value shouldBe mockServicios
//            // ASSERT: Verificar que el mensaje de error se haya reseteado
//            viewModel.errorMessage.value shouldBe null
//        }
//    }
//
//    // --- PRUEBA 2: listarServicios - Fallo ---
//    test("listarServicios debe devolver lista vacía y establecer el mensaje de error") {
//        runTest {
//            // ARRANGE: Configurar el mock para lanzar una excepción al ser llamado
//            val exception = RuntimeException("Fallo de red simulado")
//            coEvery { mockRepository.obtenerServiciosBackend() } throws exception
//
//            // ACT: Llamar a la función del ViewModel
//            viewModel.listarServicios()
//
//            // Ejecutar corrutina interna del ViewModel
//            testScheduler.runCurrent()
//
//            // ASSERT: Verificar que la lista de servicios esté vacía
//            viewModel.servicios.value shouldBe emptyList()
//            // ASSERT: Verificar que el StateFlow 'errorMessage' contenga el mensaje de la excepción
//            viewModel.errorMessage.value shouldBe "Error al listar servicios: ${exception.message}"
//        }
//    }
//
//    // --- PRUEBA 3: cargarServicioParaEdicion - Éxito ---
//    test("cargarServicioParaEdicion debe cargar un servicio en _servicioAEditar") {
//        runTest {
//            val servicioDetalle = mockServicios.first()
//            val id = servicioDetalle.id!!
//
//            // ARRANGE: Configurar el mock para devolver un servicio específico por ID
//            coEvery { mockRepository.obtenerServicioPorIdBackend(id) } returns servicioDetalle
//
//            // ACT: Llamar a la función del ViewModel
//            viewModel.cargarServicioParaEdicion(id)
//
//            testScheduler.runCurrent()
//
//            // ASSERT: Verificar que el StateFlow 'servicioAEditar' se haya actualizado
//            viewModel.servicioAEditar.value shouldBe servicioDetalle
//            // ASSERT: Verificar que no haya error
//            viewModel.errorMessage.value shouldBe null
//        }
//    }
//
//    // --- PRUEBA 4: crearServicio - Éxito ---
//    test("crearServicio debe agregar el nuevo servicio a la lista existente") {
//        runTest {
//            // ARRANGE: Primero, asegurar que la lista de servicios esté limpia/vacía antes de agregar
//            viewModel.listarServicios()
//            testScheduler.runCurrent()
//
//            val nuevoServicio = Servicio(
//                id = 3L,
//                nombre = "Nuevo",
//                precio = 50.0,
//                descripcion = "New",
//                activo = true
//            )
//
//            // ARRANGE: Configurar el mock para devolver el servicio que 'simula' haber sido creado
//            // Utilizamos 'any()' ya que no nos importa el objeto exacto que se le pasa a la función
//            coEvery { mockRepository.crearServicioBackend(any()) } returns nuevoServicio
//
//            // ACT: Llamar a la función del ViewModel
//            viewModel.crearServicio(nuevoServicio)
//
//            testScheduler.runCurrent()
//
//            // ASSERT: Verificar que el nuevo servicio esté ahora en la lista 'servicios' del ViewModel
//            viewModel.servicios.value shouldBe listOf(nuevoServicio)
//        }
//    }
//
//    // Al final de la suite, restablecer el dispatcher (buena práctica para no afectar otros tests)
//    afterSpec {
//        Dispatchers.resetMain()
//    }
//})