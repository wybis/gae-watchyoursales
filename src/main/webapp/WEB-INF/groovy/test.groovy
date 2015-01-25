import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Role


println '''
<html><head><title>Test</title><head><body><pre>
'''
println '-----------------------------------------------------------------'
try {
	SessionUserDto sessionUser = new SessionUserDto()
	sessionUser.id = 'munmin2000@maxmoney'
	sessionUser.roleId = Role.AGENCY_MANAGER

	//	def entitys = datastore.execute {
	//		from Product.class.simpleName
	//		where agencyId == 1
	//	}
	//	entitys.each { entity ->
	//		Product model = entity as Product
	//		stockService.onProductCreate(sessionUser, model)
	//	}

	//	Employee employee = new Employee();
	//	Class clazz = employee.getClass()
	//	println clazz
	//	println clazz.getName()
	//	println clazz.getSimpleName()
	//
	//	clazz = Employee.class
	//	println clazz
	//	println clazz.name
	//	println clazz.getSimpleName()
	//	println clazz.simpleName

	//	def entitys = datastore.execute {
	//		from Employee.class.getSimpleName()
	//		where agencyId == 1
	//	}
	//
	//	entitys.each { entity ->
	//		Employee employee = entity as Employee
	//		println employee
	//	}

}
catch(Throwable t) {
	t.printStackTrace(out)
}
println '-----------------------------------------------------------------'

println '''
</pre></body></html>
'''