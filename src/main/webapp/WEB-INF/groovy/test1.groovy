import io.vteial.wys.model.Tran
import io.vteial.wys.model.constants.TransactionCategory


println '''
<html><head><title>Test</title><head><body><pre>
'''
println '-----------------------------------------------------------------'
try {
	def entitys = datastore.execute {
		from Tran.class.simpleName
		where category == TransactionCategory.CUSTOMER
		and branchId == 1
		sort desc by date
	}

	println("dsize = " + entitys.size())
}
catch(Throwable t) {
	t.printStackTrace(out)
}
println '-----------------------------------------------------------------'

println '''
</pre></body></html>
'''

