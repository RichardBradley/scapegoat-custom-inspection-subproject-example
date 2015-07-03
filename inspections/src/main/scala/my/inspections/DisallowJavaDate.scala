package my.inspections

import com.sksamuel.scapegoat._

/**
 * A compile-time [[Inspection]] which detects usage of java.util.Date
 * as an example.
 */
class DisallowJavaDate extends Inspection {

  def inspector(context: InspectionContext): Inspector = new Inspector(context) {

    import context.global._

    override def postTyperTraverser = Some(new context.Traverser {

      private def isAkkaLogger(tpe: Type): Boolean = {
        tpe.underlying.toString() == "akka.event.LoggingAdapter" ||
          tpe.underlying.toString() == "msw.mpaf.logging.MpafLogging" ||
          tpe.parents.exists(isAkkaLogger)
      }

      override def inspect(tree: Tree): Unit = tree match {

        case Apply(fun, args) =>
          println(s"qq fun = $fun args = $args")
        case _ => continue(tree)
      }
    })
  }
}
