import io.vteial.wys.model.Account
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType


println '''
<html><head><title>Test</title><head><body><pre>
'''
println '-----------------------------------------------------------------'
try {
	def entitys = datastore.execute {
		from User.class.simpleName
		where agencyId == 1
		and type == UserType.EMPLOYEE
	}

	entitys.each { entity ->
		User model = entity as User
		def stock = Account.get(model.stockId)
		println "$model.id $model.userId $model.stockId $stock.type"
	}
}
catch(Throwable t) {
	t.printStackTrace(out)
}
println '-----------------------------------------------------------------'

println '''
</pre></body></html>
'''

