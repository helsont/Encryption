package utils;

import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import frames.MainFrame;

public class Progress {
	private ProgressMonitor progressMonitor;
	private net.lingala.zip4j.progress.ProgressMonitor monitor;
	private Task task;
	private Callback c;
	public Progress(net.lingala.zip4j.progress.ProgressMonitor monitor, Callback c) {
		this.monitor = monitor;
		progressMonitor = new ProgressMonitor(MainFrame.frame,
				"Encrypting file...", "", 0, 100);
		progressMonitor.setProgress(0);
		this.c = c;
		task = new Task(monitor);
		task.execute();
	}

	class Task extends SwingWorker<Void, Void> {
		net.lingala.zip4j.progress.ProgressMonitor monitor;

		public Task(net.lingala.zip4j.progress.ProgressMonitor monitor) {
			this.monitor = monitor;
		}

		@Override
		public Void doInBackground() {
			int progress = 0;
			try {
				c.run();
				while (progress < 100 && !isCancelled()) {
					while (monitor.getState() == net.lingala.zip4j.progress.ProgressMonitor.STATE_BUSY) {
						// To get the percentage done
						System.out.println("Percent Done: "
								+ monitor.getPercentDone());

						// To get the current file being processed
						System.out.println("File: "
								+ monitor.getFileName());
						// Sleep for up to one second.
						Thread.sleep(250);
						// Make random progress.
						setProgress(monitor.getPercentDone() * 100);
					}
					
				}
			} catch (InterruptedException ignore) {
			}
			return null;
		}

		@Override
		public void done() {
			progressMonitor.setProgress(0);
		}
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	// public void propertyChange(PropertyChangeEvent evt) {
	// if ("progress" == evt.getPropertyName()) {
	// int progress = (Integer) evt.getNewValue();
	// progressMonitor.setProgress(progress);
	// String message = String.format("Completed %d%%.\n", progress);
	// progressMonitor.setNote(message);
	// taskOutput.append(message);
	// if (progressMonitor.isCanceled() || task.isDone()) {
	// Toolkit.getDefaultToolkit().beep();
	// if (progressMonitor.isCanceled()) {
	// task.cancel(true);
	// taskOutput.append("Task canceled.\n");
	// } else {
	// taskOutput.append("Task completed.\n");
	// }
	// startButton.setEnabled(true);
	// }
	// }
	//
	// }
}
