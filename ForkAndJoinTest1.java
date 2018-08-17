import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ForkAndJoinTest1 {

	class Response {
		public String response;

		@Override
		public String toString() {
			return "Response [response=" + response + "]";
		}
	}

	class MyRecursiveTask extends RecursiveTask<List<Response>> {

		private List<Callable<String>> callableList;
		ScheduledExecutorService scheduledExecutorService;

		public MyRecursiveTask(List<Callable<String>> callableList,ScheduledExecutorService scheduledExecutorService) {
			this.callableList = callableList;
			this.scheduledExecutorService=scheduledExecutorService;
			//this.scheduledExecutorService =Executors.newScheduledThreadPool(callableList.size());
		}

		protected List<Response> compute() {

			// if work is above threshold, break tasks up into smaller tasks
			if (this.callableList.size() > 1) {

				List<MyRecursiveTask> subtasks = new ArrayList<MyRecursiveTask>();
				subtasks.addAll(createSubtasks());

				for (MyRecursiveTask subtask : subtasks) {
					subtask.fork();
				}

				List<Response> responseList = new ArrayList<Response>();
				for (MyRecursiveTask subtask : subtasks) {
					responseList.addAll(subtask.join());
				}
				scheduledExecutorService.shutdownNow();
				return responseList;

			} else {
				List<Response> responseList = new ArrayList<Response>();
				Response r = new Response();
				ScheduledFuture<String> scheduledFuture = scheduledExecutorService.schedule(callableList.get(0),10,TimeUnit.SECONDS);
					
					try {
						r.response=scheduledFuture.get();
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				responseList.add(r);
				return responseList;
			}
		}

		private List<MyRecursiveTask> createSubtasks() {
			List<MyRecursiveTask> subtasks = new ArrayList<MyRecursiveTask>();

			this.callableList.forEach(callable -> {
				MyRecursiveTask subTask = new MyRecursiveTask(
						new ArrayList<Callable<String>>() {
							private static final long serialVersionUID = 1L;
							{
								add(callable);
							}
						}, this.scheduledExecutorService);
				subtasks.add(subTask);
			});
			return subtasks;
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);
		List<Callable<String>> callableList = new ArrayList<Callable<String>>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Callable<String>() {
			        public String call() throws Exception {
			        	System.out.println("1");
			            return "Called1";
			        }
			    });
				add(new Callable<String>() {
			        public String call() throws Exception {
			        	System.out.println("2");
			            return "Called2";
			        }
			    });
				add(new Callable<String>() {
			        public String call() throws Exception {
			        	System.out.println("3");
			            return "Called3";
			        }
			    });
				add(new Callable<String>() {
			        public String call() throws Exception {
			        	System.out.println("4");
			            return "Called4";
			        }
			    });
			}
		};

		ScheduledExecutorService scheduledExecutorService =
        Executors.newScheduledThreadPool(callableList.size() + 1);
		MyRecursiveTask myRecursiveTask = new ForkAndJoinTest1().new MyRecursiveTask(
				callableList, scheduledExecutorService);
		List<Response> responseList = forkJoinPool.invoke(myRecursiveTask);
		System.out.println("mergedResult = " + responseList);
		System.out.println("here");
		scheduledExecutorService.shutdownNow();

	}
}
