package org.vidtec.rfc3550.rtcp.types.report;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;
import org.vidtec.rfc3550.rtcp.types.RTCPPacket.PayloadType;

@Test
public class SenderReportRTCPPacketTest 
{

	
	public void testCanCastSelfToConcreteType()
	{
		final SenderReportRTCPPacket r = SenderReportRTCPPacket.builder()
				.withSsrc(20)
				.build();
		
		final SenderReportRTCPPacket p = r.asConcreteType();
		assertEquals(p.packetLength(), 28, "incorrect packet length");
	}

	public void testCanCreateEmptySRPacketFromBuilder()
	{
		final byte[] data = { (byte)0x80, (byte)0xC8, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x14,
				0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02,
				0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x04,
			    0x00, 0x00, 0x00, 0x05
	               };

		final SenderReportRTCPPacket r = SenderReportRTCPPacket.builder()
				.withSsrc(20)
				.withTimestamps(0x0100000002L, 0x03)
				.withCounts(0x04, 0x05)
				.build();
		
		assertEquals(r.packetLength(), 28, "incorrect packet length");
		assertTrue(r.is(PayloadType.SR), "incorrect payload type");
		assertTrue(!r.is(PayloadType.SDES), "incorrect payload type");
		assertTrue(!r.is(null), "incorrect payload type");
		assertEquals(r.payloadType(), PayloadType.SR, "incorrect payload type");

		assertTrue(r.blocks() != null, "incorrect blocks data");
		assertTrue(r.blocks().isEmpty(), "incorrect blocks data");
		assertTrue(!r.hasBlocks(), "incorrect blocks data");
		assertEquals(r.blockCount(), 0, "incorrect blocks data");

		assertEquals(r.ssrcSenderIdentifier(), 20, "incorrect ssrc sender");
		assertEquals(r.ntpTimestamp(), 0x0000000100000002L, "incorrect timestamp");
		assertEquals(r.rtpTimestamp(), 0x03, "incorrect timestamp");
		assertEquals(r.packetCount(), 0x04, "incorrect count");
		assertEquals(r.octetCount(), 0x05, "incorrect count");
		
		assertEquals(r.asByteArray(), data, "packet data not reformed correctly.");
	}
	
	

	public void testCanCreateEmptySRPacketFromBuilderWithBadBlockImputs()
	{
		final byte[] data = { (byte)0x80, (byte)0xC8, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x14,
				0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02,
				0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x04,
			    0x00, 0x00, 0x00, 0x05
	               };
		SenderReportRTCPPacket r = SenderReportRTCPPacket.builder()
				.withSsrc(20)
				.withTimestamps(0x0100000002L, 0x03)
				.withCounts(0x04, 0x05)
				.withReportBlocks((List<ReportBlock>)null)
				.build();
		
		assertEquals(r.packetLength(), 28, "incorrect packet length");
		assertTrue(r.is(PayloadType.SR), "incorrect payload type");
		assertTrue(!r.is(PayloadType.SDES), "incorrect payload type");
		assertTrue(!r.is(null), "incorrect payload type");
		assertEquals(r.payloadType(), PayloadType.SR, "incorrect payload type");

		assertTrue(r.blocks() != null, "incorrect blocks data");
		assertTrue(r.blocks().isEmpty(), "incorrect blocks data");
		assertTrue(!r.hasBlocks(), "incorrect blocks data");
		assertEquals(r.blockCount(), 0, "incorrect blocks data");

		assertEquals(r.ssrcSenderIdentifier(), 20, "incorrect ssrc sender");
		assertEquals(r.ntpTimestamp(), 0x0000000100000002L, "incorrect timestamp");
		assertEquals(r.rtpTimestamp(), 0x03, "incorrect timestamp");
		assertEquals(r.packetCount(), 0x04, "incorrect count");
		assertEquals(r.octetCount(), 0x05, "incorrect count");
	
		assertEquals(r.asByteArray(), data, "packet data not reformed correctly.");
		

		r = SenderReportRTCPPacket.builder()
				.withSsrc(20)
				.withTimestamps(0x0100000002L, 0x03)
				.withCounts(0x04, 0x05)
				.withReportBlocks((ReportBlock[])null)
				.build();
		
		assertEquals(r.packetLength(), 28, "incorrect packet length");
		assertTrue(r.is(PayloadType.SR), "incorrect payload type");
		assertTrue(!r.is(PayloadType.SDES), "incorrect payload type");
		assertTrue(!r.is(null), "incorrect payload type");
		assertEquals(r.payloadType(), PayloadType.SR, "incorrect payload type");

		assertTrue(r.blocks() != null, "incorrect blocks data");
		assertTrue(r.blocks().isEmpty(), "incorrect blocks data");
		assertTrue(!r.hasBlocks(), "incorrect blocks data");
		assertEquals(r.blockCount(), 0, "incorrect blocks data");

		assertEquals(r.ssrcSenderIdentifier(), 20, "incorrect ssrc sender");
		assertEquals(r.ntpTimestamp(), 0x0000000100000002L, "incorrect timestamp");
		assertEquals(r.rtpTimestamp(), 0x03, "incorrect timestamp");
		assertEquals(r.packetCount(), 0x04, "incorrect count");
		assertEquals(r.octetCount(), 0x05, "incorrect count");
		
		assertEquals(r.asByteArray(), data, "packet data not reformed correctly.");
	}
	


	public void testCanCreateEmptySRPacketFromBuilderAtLimits()
	{
		final byte[] data = {
				(byte)0x9F, (byte)0xC8, 0x03, 0x04, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
				(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
				(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
			    (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,	
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00		
		};


		final byte[] blockdata = { 0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02,
									0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00 };

		final ReportBlock b = ReportBlock.fromByteArray(blockdata);
		
		final SenderReportRTCPPacket r = SenderReportRTCPPacket.builder()
				.withReportBlocks(Arrays.asList( b, b, b, b, b, b, b, b, b, b, b, b, b ))
				.withReportBlocks( b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b)
				.withSsrc(0xFFFFFFFFL)
				.withTimestamps(0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFL)
				.withCounts(0xFFFFFFFFL, 0xFFFFFFFFL)
				.build();
		
		assertEquals(r.packetLength(), 772, "incorrect packet length");
		assertTrue(r.is(PayloadType.SR), "incorrect payload type");
		assertTrue(!r.is(PayloadType.SDES), "incorrect payload type");
		assertTrue(!r.is(null), "incorrect payload type");
		assertEquals(r.payloadType(), PayloadType.SR, "incorrect payload type");

		assertTrue(r.blocks() != null, "incorrect blocks data");
		assertTrue(!r.blocks().isEmpty(), "incorrect blocks data");
		assertEquals(r.blocks().size(), 31, "incorrect blocks size");
		assertTrue(r.hasBlocks(), "incorrect blocks data");
		assertEquals(r.blockCount(), 31, "incorrect blocks data");

		assertEquals(r.ssrcSenderIdentifier(), 0xFFFFFFFFL, "incorrect ssrc sender");
		assertEquals(r.ntpTimestamp(), 0xFFFFFFFFFFFFFFFFL, "incorrect timestamp");
		assertEquals(r.rtpTimestamp(), 0xFFFFFFFFL, "incorrect timestamp");
		assertEquals(r.packetCount(), 0xFFFFFFFFL, "incorrect count");
		assertEquals(r.octetCount(), 0xFFFFFFFFL, "incorrect count");

		assertEquals(r.asByteArray(), data, "packet data not reformed correctly.");		
	}
		
	
	public void testCanCreateSimpleSRPacketFromBuilder()
	{
		final byte[] data = { 		(byte)0x82, (byte)0xC8, 0x00, 0x4C, 0x00, 0x00, 0x00, 0x14,
									0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02,
									0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x04,
								    0x00, 0x00, 0x00, 0x05,
								    0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,
								    0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00
		};

		final byte[] blockdata = { 0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02,
									0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00 };

		final ReportBlock b = ReportBlock.fromByteArray(blockdata);
		
		final SenderReportRTCPPacket r = SenderReportRTCPPacket.builder()
				.withReportBlocks(Arrays.asList( b ))
				.withReportBlocks( b )
				.withSsrc(20)
				.withTimestamps(0x0100000002L, 0x03)
				.withCounts(0x04, 0x05)
				.build();
		
		assertEquals(r.packetLength(), 76, "incorrect packet length");
		assertTrue(r.is(PayloadType.SR), "incorrect payload type");
		assertTrue(!r.is(PayloadType.SDES), "incorrect payload type");
		assertTrue(!r.is(null), "incorrect payload type");
		assertEquals(r.payloadType(), PayloadType.SR, "incorrect payload type");

		assertTrue(r.blocks() != null, "incorrect blocks data");
		assertTrue(!r.blocks().isEmpty(), "incorrect blocks data");
		assertEquals(r.blocks().size(), 2, "incorrect blocks size");
		assertTrue(r.hasBlocks(), "incorrect blocks data");
		assertEquals(r.blockCount(), 2, "incorrect blocks data");

		assertEquals(r.ssrcSenderIdentifier(), 20, "incorrect ssrc sender");
		assertEquals(r.ntpTimestamp(), 0x0000000100000002L, "incorrect timestamp");
		assertEquals(r.rtpTimestamp(), 0x03, "incorrect timestamp");
		assertEquals(r.packetCount(), 0x04, "incorrect count");
		assertEquals(r.octetCount(), 0x05, "incorrect count");
	
		assertEquals(r.asByteArray(), data, "packet data not reformed correctly.");
	}
	
	
// TODO:  what if packet too long for data ? e.g. below but with 0x80 - should fail validation ??	
	
	public void testCanCreateSimpleSRPacketFromByteArray()
	{
		final byte[] data = { (byte)0x82, (byte)0xC8, 0x00, 0x4C, 0x00, 0x00, 0x00, 0x14,
			0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02,
			0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x04,
		    0x00, 0x00, 0x00, 0x05,
		    0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,
		    0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00
		};

		final SenderReportRTCPPacket r = SenderReportRTCPPacket.fromByteArray(data);


		assertEquals(r.packetLength(), 76, "incorrect packet length");
		assertTrue(r.is(PayloadType.SR), "incorrect payload type");
		assertTrue(!r.is(PayloadType.SDES), "incorrect payload type");
		assertTrue(!r.is(null), "incorrect payload type");
		assertEquals(r.payloadType(), PayloadType.SR, "incorrect payload type");
	
		assertTrue(r.blocks() != null, "incorrect blocks data");
		assertTrue(!r.blocks().isEmpty(), "incorrect blocks data");
		assertEquals(r.blocks().size(), 2, "incorrect blocks size");
		assertTrue(r.hasBlocks(), "incorrect blocks data");
		assertEquals(r.blockCount(), 2, "incorrect blocks data");
	
		assertEquals(r.ssrcSenderIdentifier(), 20, "incorrect ssrc sender");
		assertEquals(r.ntpTimestamp(), 0x0000000100000002L, "incorrect timestamp");
		assertEquals(r.rtpTimestamp(), 0x03, "incorrect timestamp");
		assertEquals(r.packetCount(), 0x04, "incorrect count");
		assertEquals(r.octetCount(), 0x05, "incorrect count");
	
		assertEquals(r.asByteArray(), data, "packet data not reformed correctly.");
	}

	
	public void testCanCreateSimpleSRPacketFromByteArrayAtLimits()
	{
		final byte[] data = {
				(byte)0x9F, (byte)0xC8, 0x03, 0x04, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
				(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
				(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
			    (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,	
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00,		
				0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02, 0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00		
		};

		final SenderReportRTCPPacket r = SenderReportRTCPPacket.fromByteArray(data);
		
		assertEquals(r.packetLength(), 772, "incorrect packet length");
		assertTrue(r.is(PayloadType.SR), "incorrect payload type");
		assertTrue(!r.is(PayloadType.SDES), "incorrect payload type");
		assertTrue(!r.is(null), "incorrect payload type");
		assertEquals(r.payloadType(), PayloadType.SR, "incorrect payload type");

		assertTrue(r.blocks() != null, "incorrect blocks data");
		assertTrue(!r.blocks().isEmpty(), "incorrect blocks data");
		assertEquals(r.blocks().size(), 31, "incorrect blocks size");
		assertTrue(r.hasBlocks(), "incorrect blocks data");
		assertEquals(r.blockCount(), 31, "incorrect blocks data");

		assertEquals(r.ssrcSenderIdentifier(), 0xFFFFFFFFL, "incorrect ssrc sender");
		assertEquals(r.ntpTimestamp(), 0xFFFFFFFFFFFFFFFFL, "incorrect timestamp");
		assertEquals(r.rtpTimestamp(), 0xFFFFFFFFL, "incorrect timestamp");
		assertEquals(r.packetCount(), 0xFFFFFFFFL, "incorrect count");
		assertEquals(r.octetCount(), 0xFFFFFFFFL, "incorrect count");

		assertEquals(r.asByteArray(), data, "packet data not reformed correctly.");
	}
	
	
	public void testCanValidatesCorrectlyFromByteArray() throws UnknownHostException
	{
		try
		{
			SenderReportRTCPPacket.fromByteArray( null );
			fail("Expected error");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(e.getMessage(), "packet data cannot be null", "wrong validation message");
		}
		try
		{
			SenderReportRTCPPacket.fromByteArray(new byte[] { (byte)0x80 } );
			fail("Expected error");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(e.getMessage(), "Packet too short, expecting at least 28 bytes, but found 1", "wrong validation message");
		}
		try
		{
			
			SenderReportRTCPPacket.fromByteArray(new byte[] {  (byte)0x82, (byte)0xC8, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x14,
																	0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02,
																	0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x04,
																    0x00, 0x00, 0x00, 0x05 } );
			fail("Expected error");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(e.getMessage(), "Packet states 2 report blocks, so expecting length 76, but only found 28 bytes.", "wrong validation message");
		}
		try
		{
			SenderReportRTCPPacket.fromByteArray(new byte[] {  (byte)0x80, (byte)0xD8, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x14,
																	0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02,
																	0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x04,
																    0x00, 0x00, 0x00, 0x05 } );
			fail("Expected error");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(e.getMessage(), "Invalid or unexpected packet type - should be 200", "wrong validation message");
		}
		try
		{
			SenderReportRTCPPacket.fromByteArray(new byte[] {  (byte)0x80, (byte)0xC8, 0x00, 0x1E, 0x00, 0x00, 0x00, 0x14,
																	0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02,
																	0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x04,
																    0x00, 0x00, 0x00, 0x05 } );
			fail("Expected error");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(e.getMessage(), "Packet states 30 bytes length, but actual length is 28", "wrong validation message");
		}
		try
		{
			SenderReportRTCPPacket.fromByteArray(new byte[] {  (byte)0xA0, (byte)0xC8, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x14,
																	0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02,
																	0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x04,
																    0x00, 0x00, 0x00, 0x05 } );
			fail("Expected error");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(e.getMessage(), "SR packet should never be padded, malformed packet found", "wrong validation message");
		}
	} 
	
	
	public void testCanValidatesCorrectlyFromBuilder() throws UnknownHostException
	{
		try
		{
			// builder pattern too many blocks
			final byte[] blockdata = { 0x04, 0x03, 0x02, 0x01, 0x01, 0x03, 0x02, 0x01, 0x05, 0x04, 0x03, 0x02,
										0x06, 0x03, 0x02, 0x01, 0x07, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00 };
	
			final ReportBlock b = ReportBlock.fromByteArray(blockdata);
			SenderReportRTCPPacket.builder()
					.withReportBlocks(Arrays.asList( b ))
					.withReportBlocks( b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b)
					.withSsrc(20)
					.build();

			fail("Expected error");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals(e.getMessage(), "maximum report block size exceeded, expected at most 31, but was 34", "wrong validation message");
		}
		
	}
	

}