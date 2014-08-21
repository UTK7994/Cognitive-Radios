import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

class Primary_Secondary_Arrival
{
	public static Queue<Packet> q3, q2, q1;
	private static int s3 = 0, s2 = 0, s1 = 0;
	private static Scanner sc;
	private static int i = 1, tol = 0, j = 0;
	public static Packet[] p;
	private static PrintWriter out;
	private static int n, id;
	static int time = 0, timestamp = 0, collisions;
	static public Thread t1 = null;
	static Thread tu;
	static volatile boolean isUpdateRunning = false;

	static int p_invasion = 1;// first time primary user is coming
	// tu.start();
	static float k1 = 1;
	static int packetLine3, packetLine2, packetLine1;
	static int packet_passed = 0, packet_no;
	static double[] arr;

	public static void main(String[] args) throws Exception
	{
		System.out.println(Thread.currentThread().getName());
		sc = new Scanner(System.in);
		out = new PrintWriter(new FileWriter(
				"output.txt"));
		print("Enter the number of packets");
		n = sc.nextInt();
		p = new Packet[n + 1]; // 0.1 Million Packets

		// init 3 queues q3>q2>q1
		q3 = new LinkedList<Packet>();
		q2 = new LinkedList<Packet>();
		q1 = new LinkedList<Packet>();

		// ===============================================================================================//

		BufferedReader reader = new BufferedReader(new FileReader(
				"p.txt"));
		String line = null;
		int lines = 0;
		//int size = 0;

		while ((line = reader.readLine()) != null)
		{
			lines++;
		}

		arr = new double[lines + 1];

		int i1 = 1;
		line = null;
		reader = new BufferedReader(new FileReader(
				"p.txt"));

		while ((line = reader.readLine()) != null)
		{
			arr[i1++] = Double.parseDouble(line);
		}

		System.out.println("Done with that");

		// ==============================================================================================//

		print("\nStarted Generating packets" + "\nStarted adding in queue");
		// p[0].description = "Null packet";
		int start = (int) System.currentTimeMillis();
		t1 = new Thread();

		do
		{
			// Time gap between 2 packets generation

			// tolerance lies between 1 to 30
			p[i] = new Packet(i, tol = Rand(1, 30), time,
					timestamp = ((int) System.currentTimeMillis() - start));
			p[i].entering_Queue_Time = System.currentTimeMillis();

			if (tol >= 1 && tol <= 10)
			{
				q3.add(p[i]);
				p[i].q_no = 3;
				System.out.println("the size of q3 is " + (++s3));
				// call
				// Thread.sleep(100/11);
			} else if (tol > 10 && tol <= 20)
			{
				q2.add(p[i]);
				p[i].q_no = 2;
				System.out.println("the size of q2 is " + (++s2));
			} else if (tol > 20 && tol <= 30)
			{
				q1.add(p[i]);
				p[i].q_no = 1;
				System.out.println("the size of q1 is " + (++s1));
			}
			
			i++;
		} while (((int) System.currentTimeMillis() - start <= 1000) && i <= n);
		

		print("Finished Generating Packets"
				+ " Finished entering in the queue\n");

		// ==============================================================================================//
		

		System.out.println("Well the users have going to arrive!!");

		Start_pu_su();

		// ==============================================================================================//

		n = --i;
		print("value of n is " + n);
		// change number of packets
		print("the number of packets gone is " + i);

		// The switch-case looping prompt
		do
		{
			System.out.println("So the total collisions are "+collisions);
			print("\n\nenter the options..... \n\n1.show the object data\n2.File The data\n3.Start Updating");
			switch (j = sc.nextInt())
			{
				case 1:
					print("Get data of the packets 0 to finish");
					show();
					/*
					 * out.print("Hello "); out.println("world"+p[1].new_data);
					 * out.close();
					 */
					print("Exited!!!!");
					break;
				case 2:
					filing();
					break;
				case 3:
					// update();
					break;
				default:
					break;
			}
		} while (j != 100);

	}

	private static void Start_pu_su() throws InterruptedException
	{

		if (k1 <= 100)
		{
			System.out.println("The slot number is " + k1);
			//
			packetLine2 = Bin(k1 + 1.0f, arr, 1, arr.length);
			System.out.println("Current slot is " + k1 + "Here  the slot is ");
			//
			System.out.println("The packet line before " + (k1 + 1.0f) + " is "
					+ packetLine2);

			int start_slot = (int) k1;
			int final_slot = (int) k1;// assume both are same

			if (packetLine2 % 2 == 1)
			{
				
				System.out.println("user " + (packetLine2 + 1) / 2
						+ " was busy here!!");
				//
				while ((packetLine3 = Bin(k1 + 1.0f, arr, 1, 244)) % 2 == 1)
				{
					System.out
							.println("In start_pusn while loop and the slot is "
									+ k1);
					//
					packetLine2 = Bin(k1 + 1.0f, arr, 1, arr.length);
					System.out.println("From slot " + k1 + " moved to "
							+ (k1 + 1) + " the pu " + (packetLine2 + 1) / 2
							+ " were still busy");
					Thread.sleep(1000);
					k1++;
				}

				System.out
						.println("Found Successful slot for Sns after searching");

				final_slot = (int) k1;
				System.out.println("The Primary user reigned from "
						+ start_slot + " Final slot is " + final_slot);
			}

			// p_invasion += 1 + (packetLine2 - packetLine1) / 2;//how many
			// packets passed

			if (s1 + s2 + s3 != 0)
			{
				System.out.println("Calling update");
				isUpdateRunning = true;
				//
				update(0, System.currentTimeMillis());
				isUpdateRunning = false;

				// int packetLine3=Bin(final_slot+1.2f,arr,1,244);
				System.out
						.println("===========================================================");
				//
				System.out.println("The packet that are going to be lost from "
						+ (final_slot + 1.0f) + " to " + (final_slot + 2.0f));
				//
				int just_before_the_snStart = Bin(final_slot + 1.0f, arr, 1,
						244);// initialize with the
				// last line
				int packet_line_after_sn_stops = Bin(final_slot + 1, arr, 1,
						244);// final line before k+0.2

				if (packet_line_after_sn_stops - just_before_the_snStart == 0)
				{
					System.out.println("No packet came when sn was busy");
					System.out
							.println("Indexes before the update start and end should be same and are "
									+ just_before_the_snStart
									+ "  "
									+ packet_line_after_sn_stops);
					System.out.println("No increment in the coollision ");
				} else
				{
					// just_after_the_snStart = packetLine3 + 1;
					System.out
							.println("Indexes before the update start and end SHOULD NOT be same and are "
									+ just_before_the_snStart
									+ "  "
									+ packet_line_after_sn_stops);
					System.out.println(" Collisions made here are "
							+ (packet_line_after_sn_stops
									- just_before_the_snStart + 1) / 2);
					collisions += (packet_line_after_sn_stops
							- just_before_the_snStart + 1) / 2;
					System.out.println(collisions + "th collision");
				}
			}

			k1++;
			Start_pu_su();
		} else
		{
			System.out.println("Stack Goes empty");
		}
	}

	private static int Bin(float x, double[] arr, int start, int end)
	{
		int index = 0;
		while (arr[index] < x)
		{
			index++;
		}
		index--;
		System.out.println(index + " -- index");
		return index;
	}

	private static void filing()
	{
		for (int i = 1; i <= n; i++)
		{
			// out.print surely needs try catch
			try
			{
				out.print(p[i].new_data + "\n\n");
				Thread.sleep(100);
			} catch (Exception ex)
			{
			}
		}
		out.close();
	}

	private static void show()
	{
		print("enter the Object number");

		while ((i = sc.nextInt()) > 0)
		{
			if (i <= n)
			{
				p[i].getData();
			} else
			{
				print("Packet number is not valid");
			}
		}
	}

	private static int Rand(int minimum, int maximum)
	{
		// TODO Auto-generated method stub
		Random rn = new Random();
		int n = maximum - minimum + 1;
		int i = Math.abs(rn.nextInt()) % n;
		int randomNum = minimum + i;
		return randomNum;
	}

	private static void print(String string)
	{
		// TODO Auto-generated method stub
		System.out.println(string);
	}

	// ===============================================================================================//

	private static void update(long destined, long time_at_call_update)
	{

		if (System.currentTimeMillis() - time_at_call_update < destined
				&& isUpdateRunning)
		{
			System.out.println("Current Sizes are " + s3 + " " + s2 + " " + s1);
			if (s3 + s2 + s1 != 0)
			{
				do
				{
					//
					try
					{
						System.out
								.println("Current time is "
										+ (System.currentTimeMillis() - time_at_call_update)
										+ " destines was " + destined);
						System.out.println("Emptying q3!!!");
						System.out.println("Q3 says Current Sizes are " + s3
								+ " " + s2 + " " + s1 + "     "
								+ isUpdateRunning);
						p[id = q3.poll().getId()].entering_Scheluder_Time = System
								.currentTimeMillis();

						Thread.sleep(100);
						System.out.println("packet " + id + " Wait in q3 for "
								+ (p[id].getWaitingTime() - 1));// Minus 1 for
																// the
																// algo
																// delay
						s3--;
					} catch (Exception ex)
					{
					}
				} while (System.currentTimeMillis() - time_at_call_update <= destined
						&& isUpdateRunning && s3 != 0);

				if (System.currentTimeMillis() - time_at_call_update > destined)
				{
					System.out.println("Should I RETURN");
					return;
				}

				System.out.println("the size of q3 must be 0 and actually is "
						+ s3);

				// Hello Changes are from here

				do
				{
					//
					System.out.println("Emptying q2!!!");
					System.out.println("Q2 says Current Sizes are " + s3 + " "
							+ s2 + " " + s1);
					try
					{
						p[id = q2.poll().getId()].entering_Scheluder_Time = System
								.currentTimeMillis();

						Thread.sleep(100);

						System.out.println("packet " + id + " Wait in q2 for "
								+ p[id].getWaitingTime());// Minus 1 for
															// the
															// algo
															// delay
						s2--;
					} catch (Exception ex)
					{
					}
				} while (System.currentTimeMillis() - time_at_call_update < destined
						&& s3 == 0 && s2 != 0);

				if (System.currentTimeMillis() - time_at_call_update > destined)
				{
					return;
				}

				System.out.println("There must have come some element in q3 "
						+ "\nor q2 must be empty and the sizes of 3 n 2 are "
						+ s3 + "   " + s2);

				if (s3 != 0)
				{
					System.out
							.println("Returning from q2 to q3 and size of q3 is "
									+ s3);
					update(destined
							- (System.currentTimeMillis() - time_at_call_update),
							System.currentTimeMillis());
				}

				if (s2 == 0)
				{
					System.out.println("Yes the q2 is empty");
					do
					{
						//
						System.out.println("Q3 says Current Sizes are " + s3
								+ " " + s2 + " " + s1);
						try
						{
							System.out.println("Emptying q1!!!");
							p[id = q1.poll().getId()].entering_Scheluder_Time = System
									.currentTimeMillis();

							Thread.sleep(100);

							System.out.println("packet " + id
									+ " Wait in q1 for "
									+ p[id].getWaitingTime());
							s1--;
						} catch (Exception ex)
						{
						}
					} while (System.currentTimeMillis() - time_at_call_update < destined
							&& s1 != 0 && s2 == 0 && s3 == 0);

					if (System.currentTimeMillis() - time_at_call_update > destined)
					{
						return;
					}

					update(destined
							- (System.currentTimeMillis() - time_at_call_update),
							System.currentTimeMillis());
				}
			} else
			{
				System.out.println("Now fully empty");
			}

		} else if (!isUpdateRunning
				|| System.currentTimeMillis() - time_at_call_update > destined)
		{
			System.out.println("Update just stopped!!!!");
			return;
		}
	}

}


class Packet
{
	public int id = 0;
	public int a_coff = 0;
	public String description = "";
	public int tolerance, final_tol;
	Thread t;
	public static double data[];
	public String new_data = "";
	PrintWriter out;
	public int time, timestamp;
	int size = 100;
	public int q_no = 0;
	public int in_q = 0;
	public long entering_Queue_Time = 0, entering_Scheluder_Time = 0;
	public float final_p=(float)((int) ((100.0 / tolerance) * 10000) / 10000.0);
	
	public Packet(int id, int tolerance, int time, int timestamp) throws Exception
	{
		this.id = id;
		this.time = time;
		this.timestamp = timestamp;
		this.tolerance = Math.abs(tolerance);
		data = new double[] { tolerance,
				(int) ((100.0 / tolerance) * 10000) / 10000.0, time, timestamp };
		new_data = "datas are ->>    " + data[0] + "   " + data[1] + "    "
				+ data[2] + "    " + data[3];
		final_tol = tolerance + (int) changing_wait_time();
	}

	public double[] getData()
	{
		System.out.println(new_data);
		return data;
	}

	public int getId()
	{
		return id;
	}

	public int getTol()
	{
		return final_tol = tolerance - (int) changing_wait_time();
	}
	
	public float final_priority(){
		return final_p+changing_wait_time()*(1+changing_wait_time());
	}

	public long changing_wait_time()
	{
		return System.currentTimeMillis() - entering_Queue_Time;
	}

	public long getWaitingTime()
	{
		return entering_Scheluder_Time - entering_Queue_Time;
	}

	public long getFinalTol()
	{
		return max(tolerance - (int) changing_wait_time(), 0);
	}

	private long max(int i, int j)
	{
		if (i > j)
		{
			return i;
		} else
			return j;
	}

}
