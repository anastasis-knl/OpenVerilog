module jtransmissiongatetb;
	wire y;
	reg a,control;
	jtransmissiongate jtgate(y,control,a);
	initial
	begin
		$display ("RESULT\ta\ty");

		a = 0; control = 0; # 50; // Initial value is set
		if ( y === 1'bz ) // Test for inversion
			$display ("PASS  \t%d\t%d",a,y);
		else
			$display ("FAIL  \t%d\t%d",a,y);
		control = 1; # 50; // Simply change the control signal
		control = 0; # 50; // Simply change the control signal
		control = 1; # 50; // Simply change the control signal
		control = 0; # 50; // Simply change the control signal

		a = 0; control = 1; # 50; // Initial value is set
		if ( y === 0 ) // Test for inversion
			$display ("PASS  \t%d\t%d",a,y);
		else
			$display ("FAIL  \t%d\t%d",a,y);

		a = 1; control = 0; # 50; // Another value
		if ( y === 1'bz ) // Test for inversion
			$display ("PASS  \t%d\t%d",a,y);
		else
			$display ("FAIL  \t%d\t%d",a,y);
		control = 1; # 50; // Simply change the control signal
		control = 0; # 50; // Simply change the control signal
		control = 1; # 50; // Simply change the control signal
		control = 0; # 50; // Simply change the control signal

		a = 1; control = 1; # 50; // Another value
		if ( y === 1 ) // Test for inversion
			$display ("PASS  \t%d\t%d",a,y);
		else
			$display ("FAIL  \t%d\t%d",a,y);

	end



endmodule
